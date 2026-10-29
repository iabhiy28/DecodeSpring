# Spring Internals: Bean Lifecycle, Scopes & AOP Proxies

## 1. Bean Scopes

| Scope | Frequency | Notes |
| :--- | :--- | :--- |
| **`singleton`** (Default) | 1 per Spring container | Shared across all threads; beans must remain **stateless**. |
| **`prototype`** | New instance per request | Useful when a bean needs to hold mutable state. |
| **`request` / `session`** | Per HTTP request / session | Available only in web contexts. |

---

## 2. Spring Bean Lifecycle Sequence

```
1. Instantiation          (Constructor invocation)
2. Dependency Injection   (Properties populated)
3. BeanPostProcessor      (postProcessBeforeInitialization)
4. Initialization         (@PostConstruct executed)
5. BeanPostProcessor      (postProcessAfterInitialization -> Proxy created!)
6. Ready for Use          (Servicing requests)
7. Destruction            (@PreDestroy executed on context shutdown)
```

---

## 3. AOP & Dynamic Proxies (How `@Transactional` works)

- Spring intercepts annotated methods using **CGLIB dynamic bytecode proxies**.
- The Proxy handles transaction demarcation (`begin`, `commit`, `rollback`), security checks, and caching before delegating to the target method.
- **The Self-Invocation Gotcha**: Calling an annotated method (`@Transactional`, `@Async`) from within the same class (`this.method()`) bypasses the proxy, causing the annotation to be silently ignored.
