# RESTful API Design, HTTP Semantics & Spring MVC Lifecycle

## 1. Core Principles of REST

- **Stateless**: No client context stored on server between requests.
- **Resource-Oriented URIs**: Endpoints are plural nouns (`/api/v1/orders`), not verbs.
- **HTTP Methods define operations**: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`.
- **Standardized Status Codes**: `200`, `201`, `204`, `400`, `401`, `403`, `404`, `409`, `500`.

---

## 2. Safe vs Idempotent Methods

| Method | Safe | Idempotent |
| :--- | :---: | :---: |
| `GET` | ✅ | ✅ |
| `PUT` | ❌ | ✅ |
| `DELETE` | ❌ | ✅ |
| `POST` | ❌ | ❌ |
| `PATCH` | ❌ | ❌ |

---

## 3. Spring MVC HTTP Request Lifecycle

```
HTTP Request ──► DispatcherServlet
                      │
                      ├──► HandlerMapping (Resolves matching @GetMapping method)
                      ├──► HandlerAdapter (Executes Controller method)
                      ├──► Interceptors (Pre-handle / Post-handle hooks)
                      └──► HttpMessageConverter (Jackson converts POJO to JSON)
                               │
HTTP Response ◄────────────────┘
```

---

## 4. Key Spring Web Annotations

- **`@RestController`**: Combines `@Controller` and `@ResponseBody` to serialize return values directly to JSON.
- **`@PathVariable`**: Extracts dynamic URI path segments (`/orders/{id}`).
- **`@RequestParam`**: Extracts query parameters (`/orders?status=SHIPPED&page=1`).
- **`@RequestBody`**: Deserializes incoming JSON payload into a Java DTO.
- **`@RequestHeader`**: Accesses HTTP headers (`Authorization`, `X-Correlation-Id`).
- **`ResponseEntity<T>`**: Full control over HTTP status code, headers, and response body.
