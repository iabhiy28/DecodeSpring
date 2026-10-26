# Interface vs Abstract Class in Java

## 1. Core Differences & Architecture

| Characteristic | Interface | Abstract Class |
| :--- | :--- | :--- |
| **Philosophy** | Defines a contract / capability (**"Can-Do"**) | Defines core identity and partial implementation (**"Is-A"**) |
| **Inheritance** | A class can implement **multiple** interfaces | A class can extend only **one** class (single inheritance) |
| **Instance State** | **No instance fields**. Only `public static final` constants | **Can hold instance variables** (e.g., `protected double balance;`) |
| **Constructors** | **No constructors** allowed | **Can have constructors** (invoked by subclasses using `super(...)`) |
| **Method Types** | Abstract, `default` (Java 8+), `static` (Java 8+), `private` (Java 9+) | Abstract, concrete (`public`, `protected`, `package-private`, `private`) |
| **Speed / Lookup** | Slower method resolution in JVM via `itable` | Faster method resolution in JVM via `vtable` |

---

## 2. Constructor Chaining Rule (`super`)

When an abstract class defines a parameterized constructor:
```java
abstract class BaseAccount {
    protected String accountNumber;
    public BaseAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
```
Any concrete subclass **must** explicitly call it using `super(accountNumber)`:
```java
class SavingsAccount extends BaseAccount {
    public SavingsAccount(String accountNumber) {
        super(accountNumber); // Required because BaseAccount has no no-arg constructor!
    }
}
```

---

## 3. When to Choose Which

### Choose an **Interface** when:
1. You are designing capabilities that unrelated classes can share (e.g., `Comparable`, `AutoCloseable`, `Auditable`).
2. You want to define service contracts in Spring Boot:
   ```java
   public interface UserService {
       UserResponse findById(Long id);
   }
   ```
3. You need multiple inheritance of behavior.

### Choose an **Abstract Class** when:
1. You want to share non-static fields (state) among closely related classes.
2. You need constructors to enforce valid initial state when child objects are instantiated.
3. You are implementing the **Template Method Pattern**: an algorithm skeleton where common steps are implemented in the abstract base class and customizable steps are left abstract.
