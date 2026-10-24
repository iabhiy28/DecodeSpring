# Java Memory Model — String Pool & Pass-by-Value

## Why does this exist?
The JVM needs to manage memory efficiently. Understanding how Java allocates
memory for primitives, objects, and strings is foundational to:
- Debugging unexpected equality bugs
- Understanding Spring bean scopes
- Understanding JPA persistence context
- Avoiding memory leaks

---

## 1. Stack vs Heap

| | Stack | Heap |
|--|-------|------|
| Stores | Primitive values, references | All objects |
| Lifetime | Frame-based (method call duration) | Until garbage collected |
| Size | Small, fixed | Large, dynamic |
| Speed | Very fast | Slower |
| Thread safety | Each thread has its own | Shared across threads |

```
STACK (per thread)         HEAP (shared)
┌─────────────┐            ┌────────────────────┐
│ main frame  │            │ String Pool         │
│  x = 10     │            │   "hello"           │
│  a ─────────┼────────────┼──► (String object)  │
│  b ─────────┼────────────┘                     │
└─────────────┘                                  │
                           │ [new String("hello")]│
                           └────────────────────┘
```

---

## 2. The String Pool

- A special area inside the Heap
- Stores unique String literals
- JVM reuses existing pool strings instead of creating duplicates
- Purpose: Memory optimization

### Rules:
| How String is created | Goes to pool? |
|-----------------------|---------------|
| `String s = "hello"` | ✅ Yes (literal) |
| `new String("hello")` | ❌ No (forced new heap object) |
| `s3.intern()` | ✅ Returns pool reference |

### Example:
```java
String a = "hello";          // Pool: "hello" created, a → pool
String b = "hello";          // Pool: reused, b → same pool object
String c = new String("hello"); // Heap: new object, c → new object
String d = c.intern();       // Pool: "hello" found, d → pool

a == b    // true  — same pool object
a == c    // false — pool vs new heap object
a == d    // true  — both → pool
c == d    // false — heap vs pool
a.equals(c) // true — same content
```

### intern() explained:
> `intern()` looks up the String in the pool.
> If found → returns the pool reference.
> If not found → adds it to pool, returns pool reference.

---

## 3. The Golden Rule for String Comparison

```java
// ❌ WRONG — uses reference comparison
if (status == "ACTIVE") { }

// ✅ CORRECT — uses content comparison
if (status.equals("ACTIVE")) { }

// ✅ ALSO CORRECT — null-safe version
if ("ACTIVE".equals(status)) { }  // won't throw NullPointerException if status is null
```

**Always use `.equals()` for String content comparison.**

---

## 4. String Immutability

> Once a String object is created, its content can NEVER be changed.

```java
String s = "hello";
s = s + " world";
// "hello" object still exists on heap (unchanged)
// A new String "hello world" was created
// s now points to the new one
// "hello" becomes eligible for garbage collection
```

### String vs StringBuilder

| | String | StringBuilder |
|--|--------|---------------|
| Mutable | ❌ No | ✅ Yes |
| Thread-safe | ✅ Yes | ❌ No |
| Performance (concatenation) | Poor in loops | Excellent |

```java
// BAD — O(n²) memory
String result = "";
for (int i = 0; i < n; i++) {
    result = result + i;   // new object each time!
}

// GOOD — O(n) memory
StringBuilder sb = new StringBuilder();
for (int i = 0; i < n; i++) {
    sb.append(i);          // modifies in place
}
String result = sb.toString();
```

---

## 5. Pass-by-Value

> **Java is ALWAYS pass-by-value.**
> For objects, a COPY of the reference (memory address) is passed.
> Not the object itself. Not a true reference.

### Primitives — copy of value:
```java
void change(int num) { num = 99; }

int x = 10;
change(x);
System.out.println(x); // 10 — x is unchanged
```

### Objects — copy of reference:
```java
// Modifying via the reference → AFFECTS the original
void modify(StringBuilder sb) { sb.append(" World"); }

StringBuilder s = new StringBuilder("Hello");
modify(s);
System.out.println(s); // "Hello World" — same heap object was modified

// Reassigning the reference → does NOT affect original
void replace(StringBuilder sb) { sb = new StringBuilder("new"); }

StringBuilder s2 = new StringBuilder("Hello");
replace(s2);
System.out.println(s2); // "Hello" — s2 still points to original
```

### The key insight:
```
Passed into method → copy of address (reference value)
Modifying via copy → reaches same heap object → ORIGINAL CHANGES
Reassigning copy   → copy now points elsewhere → ORIGINAL UNCHANGED
```

---

## Common Mistakes

| Mistake | Why wrong | Fix |
|---------|-----------|-----|
| `str1 == str2` | Compares references, not content | Use `.equals()` |
| String concatenation in loops | Creates n new objects | Use `StringBuilder` |
| Assuming `s = "new"` in method changes caller | Strings are immutable + pass-by-value | Return the new value |
| `new String("hello")` unnecessarily | Bypasses pool, wastes memory | Use `"hello"` literal |

---

## Interview Questions

**Q: Why does `"hello" == "hello"` return true but `new String("hello") == new String("hello")` return false?**
> The first uses String pool — same object reused. The second uses `new` — forces two separate heap objects.

**Q: Java is pass-by-reference for objects, right?**
> No. Java always passes by value. For objects, the VALUE passed is a copy of the reference (memory address). You can modify the object the reference points to, but you cannot change where the caller's variable points.

**Q: What is String interning?**
> The process of adding a String to the pool and reusing it. `intern()` explicitly forces this. String literals are automatically interned by the JVM.

**Q: Why are Strings immutable in Java?**
> 1. Thread safety — immutable objects are safe to share across threads
> 2. Security — class names, file paths, network URLs are Strings; mutability would be a security risk
> 3. Caching — hashCode of a String can be cached since it never changes
> 4. String pool — immutability enables safe sharing in the pool

---

## Quick Revision (5-minute version)

- Stack = primitives + references | Heap = objects
- String Pool = special heap area for unique String literals
- Literals → pool | `new String()` → bypasses pool
- `==` → reference equality | `.equals()` → content equality
- **Always use `.equals()` for String comparison**
- Strings are immutable — any "change" creates a new object
- Java is pass-by-value — objects pass a copy of their reference
- Modifying through a copied reference affects the original
- Reassigning a parameter inside a method never affects the caller
