# JVM Internals, Classloading, Memory & Garbage Collection

## 1. JDK vs JRE vs JVM

- **JDK**: Includes `javac` compiler, debuggers, tools, and the JRE.
- **JRE**: Includes standard libraries and the JVM.
- **JVM**: Abstract computing machine that executes compiled `.class` bytecode on native hardware.

---

## 2. JVM Runtime Memory Areas

| Area | Storage | Lifetime | Shared or Thread-Private |
| :--- | :--- | :--- | :--- |
| **Heap** | All instantiated objects, arrays | Until GC cleans up | **Shared** across all threads |
| **Metaspace** | Class structures, method bytecode, static fields | Class unload | **Shared** (Native OS memory) |
| **JVM Stack** | Local variables, method frames, primitive values | Method invocation | **Thread-Private** |
| **PC Register** | Address of current bytecode instruction | Thread lifespan | **Thread-Private** |

---

## 3. Execution Engine & The JIT Compiler

The JVM uses a hybrid approach for maximum performance:
1. **Interpreter**: Starts executing bytecode immediately without waiting for full compilation.
2. **JIT (Just-In-Time) Compiler**: Profiler tracks frequently executed methods ("Hot Spots"). JIT compiles these hot spots directly into native CPU machine instructions, enabling Java to reach near-C++ execution speed.
3. **Garbage Collector**: Runs concurrently to sweep unreachable Heap objects.

---

## 4. Garbage Collection Generations

- **Eden Space (Young Gen)**: New objects are instantiated here. Minor GC runs frequently.
- **Survivor Spaces (S0 / S1)**: Objects that survive Minor GC cycles.
- **Old Generation (Tenured)**: Long-lived objects (e.g., Spring Singleton Beans, database connection pools). Cleaned by Major GC.
- **Default Production GC**: **G1 GC** (Garbage-First) for balanced high throughput and low pause times.
