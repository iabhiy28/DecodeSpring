# Collections Framework: Hierarchy, ArrayList vs LinkedList

## 1. Hierarchy Overview

```
                 Iterable<E>
                     │
               Collection<E>
        ┌────────────┼────────────┐
        ▼            ▼            ▼
     List<E>       Set<E>      Queue<E>
    (Ordered)     (Unique)      (FIFO)
        │
   ┌────┴────┐
   ▼         ▼
ArrayList  LinkedList

* Note: Map<K, V> is a separate hierarchy (Key-Value pairs)!
```

---

## 2. ArrayList Internals

- Backed by an internal array: `transient Object[] elementData;`
- **Initial Capacity**:
  - `new ArrayList<>()`: creates an empty array buffer.
  - On the **1st insertion**: expands to default capacity = **10**.
- **Resizing Algorithm**:
  - Triggers when `size == elementData.length`.
  - Growth factor: **1.5x (50% increase)**:
    $$\text{newCapacity} = \text{oldCapacity} + (\text{oldCapacity} \gg 1)$$
  - Copies elements via fast native `System.arraycopy(...)`.

---

## 3. ArrayList vs LinkedList: Architecture & Tradeoffs

| Factor | `ArrayList` | `LinkedList` |
| :--- | :--- | :--- |
| **Data Structure** | Dynamic contiguous array | Doubly-linked list (`Node<E>` pointers) |
| **Random Access `get(i)`** | **$O(1)$** | **$O(N)$** |
| **Append `add(e)`** | **$O(1)$ amortized** | **$O(1)$** |
| **Insert at index `add(i, e)`** | $O(N)$ (shifts elements) | $O(N)$ ($O(N)$ traversal + $O(1)$ pointer update) |
| **CPU Cache Locality** | **Excellent (Contiguous memory)** | **Poor (Pointer hopping, Cache misses)** |
| **Memory Overhead** | Minimal (references in array) | Heavy (~36-48 bytes extra per node) |

> **Production Recommendation:** Always default to `ArrayList`. If you need a fast double-ended queue, prefer `ArrayDeque` over `LinkedList`.
