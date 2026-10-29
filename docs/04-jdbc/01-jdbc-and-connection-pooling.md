# JDBC Architecture, Connection Pooling & The Rise of JPA

## 1. What is JDBC?

Low-level Java database API (`java.sql`) that communicates with relational databases through database-specific vendor drivers.

```
Java Application ──► JDBC API ──► Database Driver ──► Relational DB
```

---

## 2. Statement vs PreparedStatement

| Feature | `Statement` | `PreparedStatement` |
| :--- | :--- | :--- |
| **SQL Injection** | ❌ Vulnerable to SQL Injection via string concatenation | 🛡️ **Completely Immune** (Parameters treated strictly as literal data) |
| **Execution** | Compiled by DB every time | Pre-compiled and cached by DB engine |
| **Performance** | Slower for repetitive queries | Faster (re-uses compiled execution plan) |

---

## 3. Connection Pooling (HikariCP)

- Creating TCP database connections is expensive (50-100ms).
- **HikariCP** maintains a pool of pre-established database connections.
- Threads borrow connections to execute queries and immediately return them to the pool.
- **Default in Spring Boot**: Configured automatically via `spring.datasource.hikari.*`.

---

## 4. Why ORM & JPA Were Introduced

- **Object-Relational Impedance Mismatch**: Java thinks in object graphs, inheritance, and associations; SQL thinks in 2D relational tables and foreign keys.
- **Eliminates Boilerplate**: Automates mapping `ResultSet` columns into Java entity objects.
- **Database Portability**: Generates SQL dialects dynamically for MySQL, PostgreSQL, Oracle, etc.
