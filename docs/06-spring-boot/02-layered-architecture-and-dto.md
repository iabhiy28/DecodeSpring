# Enterprise Layered Architecture, Package Structure & The DTO Pattern

## 1. Multi-Layered Architecture

```
HTTP Request (JSON)
        │
        ▼
Controller Layer (@RestController) ──► HTTP Validation (@Valid), Status codes
        │ Uses DTOs
        ▼
Service Layer (@Service)           ──► Business logic, @Transactional orchestration
        │ Interacts with Entities
        ▼
Repository Layer (@Repository)     ──► Spring Data JPA, database queries
        │
        ▼
Relational Database (PostgreSQL / MySQL)
```

---

## 2. Why NEVER Return JPA Entities from Controllers

1. **Security Vulnerability**: Exposes sensitive internal fields (e.g., password hashes, internal status flags).
2. **Infinite JSON Recursion**: Bidirectional associations (`User` $\leftrightarrow$ `Order`) cause Jackson serialization to crash with `StackOverflowError`.
3. **API Coupling**: Changing a database column name breaks mobile/frontend API contracts.
4. **LazyInitializationException**: Accessing uninitialized lazy relationships outside an active persistence context throws runtime exceptions.

---

## 3. Java Records as DTOs

Modern Java `records` are ideal for DTOs because they are inherently **immutable**, thread-safe, and auto-generate constructors, `equals()`, `hashCode()`, and `toString()`:

```java
public record CreateUserRequest(
    @NotBlank(message = "Username cannot be empty")
    String username,

    @Email(message = "Invalid email format")
    String email,

    @Size(min = 8, message = "Password must be at least 8 characters")
    String password
) {}

public record UserResponse(
    Long id,
    String username,
    String email,
    LocalDateTime createdAt
) {}
```
