# Modern Java: Lambdas, Functional Interfaces, Streams & Optional

## 1. Functional Interfaces

A Functional Interface has **exactly one abstract method** (SAM — Single Abstract Method) and is marked with `@FunctionalInterface`.

| Interface | Method Signature | Input | Output | Purpose | Lambda Example |
| :--- | :--- | :---: | :---: | :--- | :--- |
| **`Predicate<T>`** | `boolean test(T t)` | `T` | `boolean` | Condition evaluation / filtering | `age -> age >= 18` |
| **`Function<T, R>`** | `R apply(T t)` | `T` | `R` | Transforming data | `str -> str.toUpperCase()` |
| **`Consumer<T>`** | `void accept(T t)` | `T` | `void` | Side effects / Actions | `x -> System.out.println(x)` |
| **`Supplier<T>`** | `T get()` | none | `T` | Factory / Value generator | `() -> new NotFoundException()` |

---

## 2. Stream API Architecture

A Stream represents a sequence of elements supporting sequential and parallel aggregate operations. It does **not** store elements.

```
Source (List / Set) ──► Intermediate Ops (Lazy) ──► Terminal Op (Eager) ──► Result
```

### Key Operations:
- **Intermediate (Returns a new Stream, lazy execution)**:
  - `.filter(Predicate<T>)`
  - `.map(Function<T, R>)`
  - `.flatMap(Function<T, Stream<R>>)`
  - `.distinct()`, `.sorted()`, `.limit(n)`
- **Terminal (Triggers execution, produces result)**:
  - `.collect(Collectors.toList())` / `.collect(Collectors.toMap(...))`
  - `.forEach(Consumer<T>)`
  - `.reduce(...)`, `.count()`, `.findFirst()`

---

## 3. `Optional<T>`: Eliminating `NullPointerException`

> **What it is:** A container object which may or may not contain a non-null value. It is **not** a collection.

### Core Idioms in Spring Boot:
```java
// 1. Throw exception if absent (Standard REST API pattern)
User user = userRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

// 2. Fallback to default
String role = userOptional.map(User::getRole).orElse("ROLE_USER");

// 3. Action if present
userOptional.ifPresent(user -> notificationService.sendWelcome(user.getEmail()));
```
### Golden Rule:
Never call `optional.get()` without checking `isPresent()`. Prefer `.orElseThrow()`, `.orElse()`, or `.ifPresent()`.
