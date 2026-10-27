# HashMap Internals: Architecture, Collisions & The Equals/HashCode Contract

## 1. High-Level Architecture

Internally, a `HashMap` is an **array of buckets**:
```java
transient Node<K,V>[] table;
```

Each bucket slot contains a `Node<K, V>`:
```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;
    final K key;
    V value;
    Node<K,V> next; // Linked list chain for collisions
}
```

### Key Constants
- **Default Initial Capacity**: `16` (always a power of 2: 16, 32, 64...)
- **Default Load Factor**: `0.75`
- **Threshold**: $\text{Capacity} \times \text{Load Factor} = 16 \times 0.75 = 12$
- **TREEIFY_THRESHOLD**: `8` (linked list converts to Red-Black tree when $\ge 8$ and table capacity $\ge 64$)
- **UNTREEIFY_THRESHOLD**: `6` (tree converts back to linked list if it shrinks to $\le 6$)

---

## 2. What Happens During `put(key, value)`

1. **Hash Calculation**: Calls `key.hashCode()`, then applies HashMap's internal hash-spread function.
2. **Bucket Indexing**:
   $$\text{index} = (n - 1) \ \& \ \text{hash} \quad (\text{equivalent to } \text{hash} \pmod n)$$
3. **Collision Handling**:
   - **Empty slot**: Places the new `Node` directly at `table[index]`.
   - **Collision (slot occupied)**:
     - Traverses linked list / tree comparing keys:
       ```java
       if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
       ```
     - If matching key found $\to$ updates the value.
     - If end reached without match $\to$ appends new `Node`.
4. **Treeification Check**: If chain length reaches `8`, converts to Red-Black Tree ($O(\log N)$ search).
5. **Resizing Check**: If `++size > threshold`, doubles array capacity and **rehashes** all entries.

---

## 3. The `equals()` and `hashCode()` Contract

> **Contract Rule:** If two objects are equal via `.equals()`, their `.hashCode()` **MUST** return identical integer values.

### What happens if you violate the contract?
- **Override `equals()` without `hashCode()`**: Two logically equal objects will produce different hash codes $\to$ mapped to different buckets $\to$ `map.get()` returns `null`.
- **Mutable Keys**: Modifying a key object's fields after putting it into a map alters its hash code $\to$ lookups search the wrong bucket $\to$ returns `null` and causes a memory leak.

**Golden Rule:** Always use **immutable objects** (such as `String`, `UUID`, `Long`, or Java `records`) as Map keys.
