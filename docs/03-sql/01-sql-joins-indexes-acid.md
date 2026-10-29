# SQL Fundamentals, Joins, Indexing & ACID Transactions

## 1. SQL Query Execution Order

```
1. FROM & JOIN     (Resolve tables)
2. WHERE           (Filter individual rows)
3. GROUP BY        (Group into aggregate buckets)
4. HAVING          (Filter aggregated groups)
5. SELECT          (Extract columns / expressions)
6. ORDER BY        (Sort results)
7. LIMIT / OFFSET  (Paginate output)
```

### `WHERE` vs `HAVING`
- **`WHERE`**: Filters records **before** aggregation.
- **`HAVING`**: Filters groups **after** `GROUP BY` aggregation.

```sql
SELECT city, COUNT(*) AS user_count
FROM users
WHERE status = 'ACTIVE'
GROUP BY city
HAVING COUNT(*) > 5
ORDER BY user_count DESC;
```

---

## 2. SQL Joins Overview

- **`INNER JOIN`**: Matches present in both tables.
- **`LEFT JOIN`**: All rows from left table + matched right rows (or `NULL` if no match).
- **`RIGHT JOIN`**: All rows from right table + matched left rows.
- **`FULL OUTER JOIN`**: All rows from both tables.

---

## 3. Database Indexes (B-Trees)

- Drops query time complexity from $O(N)$ (Full Table Scan) to $O(\log N)$ (B-Tree lookup).
- **When to Index**: Foreign keys, unique fields (`email`, `username`), frequently queried filters.
- **Trade-off**: Slows down `INSERT`, `UPDATE`, and `DELETE` due to tree rebalancing.

---

## 4. ACID & Isolation Levels

| Level | Dirty Reads | Non-Repeatable Reads | Phantom Reads |
| :--- | :---: | :---: | :---: |
| **Read Uncommitted** | Yes | Yes | Yes |
| **Read Committed** (PostgreSQL default) | No | Yes | Yes |
| **Repeatable Read** (MySQL default) | No | No | Yes |
| **Serializable** | No | No | No |
