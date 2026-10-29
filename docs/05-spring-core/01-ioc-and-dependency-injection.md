# Spring Core: Inversion of Control (IoC) & Dependency Injection (DI)

## 1. The Core Problem Solved by Spring
In traditional Java, classes instantiate their own dependencies (`new StripeService()`), leading to:
- Tight coupling between components.
- Inability to write fast, isolated unit tests.
- Uncontrolled object lifecycles across the application.

---

## 2. Inversion of Control (IoC)
- Inversion of Control delegates the responsibility of instantiating, configuring, and assembling objects to the **Spring IoC Container** (`ApplicationContext`).
- Objects specify their dependencies purely through constructor parameters or configuration, and the container injects them.

---

## 3. What is a Spring Bean?
A **Spring Bean** is an object managed, instantiated, configured, and wired by the Spring IoC container throughout its lifecycle.

---

## 4. Stereotype Annotations

| Annotation | Semantic Role |
| :--- | :--- |
| **`@Component`** | General-purpose Spring-managed component. |
| **`@Service`** | Business logic layer. |
| **`@Repository`** | Data access layer (translates DB vendor exceptions into Spring exceptions). |
| **`@Controller`** | Presentation layer (returns HTML views or MVC templates). |
| **`@RestController`** | Specialized `@Controller` + `@ResponseBody` for REST APIs (returns JSON). |
| **`@Configuration`** | Declares a class as a source of `@Bean` definition methods. |

---

## 5. Dependency Injection Flavors

| Injection Type | Preferred? | Why? |
| :--- | :---: | :--- |
| **Constructor Injection** | ✅ **Gold Standard** | Allows `final` immutable fields, prevents partial initialization, enables pure unit testing without Spring context. |
| **Setter Injection** | ⚠️ Secondary | Useful only for truly optional or reconfigurable dependencies. |
| **Field Injection** (`@Autowired` on field) | ❌ Anti-pattern | Cannot use `final`, hides dependency complexity, makes unit testing difficult. |
