# Multithreading, Concurrency & Thread Safety in Spring Boot

## 1. Process vs Thread

- **Process**: OS-level program execution unit with isolated heap memory space.
- **Thread**: Sub-unit within a process sharing the Heap, but with its own independent Stack.
- **Spring Boot Context**: Embedded Tomcat allocates a thread per HTTP request. Service layer state must be thread-safe!

---

## 2. Race Conditions & The `count++` Trap

`count++` is not an atomic operation:
1. **Read** value from Heap memory.
2. **Modify** value in CPU register (+1).
3. **Write** value back to Heap memory.

When concurrent threads execute these steps simultaneously, updates are overwritten and lost.

---

## 3. Concurrency Tools Comparison

| Tool | Atomicity | Visibility | Blocking? | Best Used For |
| :--- | :---: | :---: | :---: | :--- |
| **`volatile`** | ❌ No | ✅ Yes | No | Status flags (`boolean running = true`) |
| **`synchronized`** | ✅ Yes | ✅ Yes | Yes (Locks) | Coordinated multi-step critical sections |
| **`AtomicInteger`** | ✅ Yes | ✅ Yes | No (CPU CAS) | High-throughput counters, metrics |
| **`ConcurrentHashMap`** | ✅ Yes | ✅ Yes | Minimal | Shared in-memory caches, lookup tables |

---

## 4. Modern Asynchronous Execution: Thread Pools

Do not create raw `new Thread()` in production. Always manage threads via thread pools:
```java
ExecutorService pool = Executors.newFixedThreadPool(10);
pool.submit(() -> processOrder(orderId));
pool.shutdown();
```

In Spring Boot, configure an async executor:
```java
@Async
public CompletableFuture<PaymentStatus> processPaymentAsync(Order order) {
    // runs on a separate worker thread
}
```
