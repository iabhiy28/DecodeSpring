# Object-Oriented Programming: Polymorphism, Keywords & Composition

## 1. Keywords: `final`, `finally`, `finalize()`

These look similar in spelling but serve completely different purposes in Java:

| Keyword / Method | Type | Context | Purpose |
| :--- | :--- | :--- | :--- |
| `final` | Keyword (Modifier) | Variables, Methods, Classes | **Immutability / Restriction**: Prevents reassignment, method overriding, or class inheritance. |
| `finally` | Keyword (Block) | `try-catch` structures | **Guaranteed execution**: Always runs after try/catch (even if returns or exceptions occur), used for resource cleanup. |
| `finalize()` | Method | `java.lang.Object` | **GC callback (Deprecated)**: Called by Garbage Collector before reclaiming memory. Do not use in modern Java. |

### The Critical `finally` Rule
`finally` runs before a method actually exits even if `return` is executed in `try` or `catch`:
```java
public int test() {
    try {
        return 1;
    } finally {
        System.out.println("Finally runs before return exits!");
    }
}
```

---

## 2. Polymorphism: Overloading vs Overriding

| Feature | Method Overloading (Static / Compile-Time) | Method Overriding (Dynamic / Runtime) |
| :--- | :--- | :--- |
| **Location** | Same class (or subclass introducing new overload) | Subclass modifying inherited behavior |
| **Method Signature** | Same method name, **different parameter list** | Same method name, **identical parameter list & compatible return type** |
| **Resolution Time** | **Compile time**: Resolved by the compiler using the reference type and argument types. | **Runtime**: Resolved by the JVM using the **actual object type** in Heap memory. |
| **Annotation** | None | `@Override` (recommended to catch signature typos) |

### The Golden Rule of Polymorphism:
- **Compiler** looks at the **Reference Type** to check if the method signature exists.
- **JVM (Runtime)** looks at the **Actual Object Type on the Heap** to execute the overridden version.

```java
Shape s = new Circle(); // Reference: Shape, Object: Circle
s.area();               // Runtime invokes Circle's area()
```

---

## 3. Composition vs Inheritance

> **"Favor object composition over class inheritance."** — *Effective Java & Gang of Four*

### The Fragile Base Class Problem (Inheritance Pitfall)
Inheritance (`extends`) creates an **IS-A** relationship with tight coupling:
- Subclasses inherit fields and methods they might not want or need (e.g., `ElectricCar` inheriting `startEngine()`).
- Changing the parent class can silently break child class logic.
- Java does not support multiple class inheritance (only single inheritance).

### Composition (HAS-A Relationship)
Composition means holding a reference to a collaborator object / interface and delegating work to it:
```java
// Flexible, swappable behavior via delegation
class MediaPlayer {
    private Playable content; // Composition: HAS-A Playable

    public MediaPlayer(Playable content) {
        this.content = content;
    }

    public void play() {
        content.play(); // Delegates task to collaborator
    }
}
```

### Why Spring Boot Relies on Composition
In enterprise Spring Boot applications, controllers, services, and repositories are combined through composition and constructor injection:
```java
@Service
public class OrderService {
    private final OrderRepository orderRepository; // HAS-A
    private final PaymentGateway paymentGateway;   // HAS-A

    public OrderService(OrderRepository repo, PaymentGateway gateway) {
        this.orderRepository = repo;
        this.paymentGateway = gateway;
    }
}
```
This enables loose coupling, modularity, and easy mocking during unit tests.
