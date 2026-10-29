# Exception Handling in Enterprise Java & Spring Boot

## 1. Exception Hierarchy

```
                       Throwable
                      /         \
                 Error           Exception
               (JVM fatal)           │
                                     ├── Checked Exceptions (Compile-time)
                                     │   - IOException, SQLException
                                     │
                                     └── Unchecked Exceptions (Runtime)
                                         RuntimeException
                                         ├── NullPointerException
                                         ├── IllegalArgumentException
                                         └── Custom Business Exceptions
```

---

## 2. Checked vs Unchecked Exceptions

| Feature | Checked Exception | Unchecked (`RuntimeException`) |
| :--- | :--- | :--- |
| **Inheritance** | Directly extends `Exception` | Extends `RuntimeException` |
| **Compiler Enforced** | Yes (Must `catch` or declare `throws`) | No |
| **Intent** | External recoverable system conditions | Logical bugs, business rule violations |
| **Industry Practice** | Avoid in modern service layers | **Preferred standard** for enterprise APIs |

---

## 3. Custom Business Exceptions in Spring Boot

Always inherit from `RuntimeException`:
```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

### Why?
Spring Boot REST controllers handle them globally via `@RestControllerAdvice`:
```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
```

---

## 4. Resource Management: `try-with-resources`

Classes implementing `AutoCloseable` should always be handled via `try-with-resources` instead of manual `finally` blocks:
```java
try (Connection conn = dataSource.getConnection();
     PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")) {
    // executes query...
} // conn and ps are automatically closed here, even if exceptions occur!
```

---

## 5. `throw` vs `throws`

- **`throw`**: An executable statement inside a method body to throw an exception instance (`throw new IllegalArgumentException("Invalid ID");`).
- **`throws`**: A method signature clause declaring that checked exceptions may propagate out of this method (`public void readFile() throws IOException`).
