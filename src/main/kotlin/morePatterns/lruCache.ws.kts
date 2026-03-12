package morePatterns

import java.util.LinkedHashMap

// note there is also an android.util.LruCache

/**
 * Least Recently Used (LRU) Cache implementation using LinkedHashMap.
 * Hash table providing O(1) access and doubly-linked list maintaining the eviction order 
 * When an item is accessed, we move it to the head of the list
 * Ideal for in-memory stores where we want to discard the least recently used items
 *
 * Time Complexity:
 * - get: O(1)
 * - put: O(1)
 *
 * Space Complexity:
 * - O(capacity) to store the elements.
 */
class LRUCache<K, V>(private val capacity: Int) : LinkedHashMap<K, V>(
    capacity, 0.75f, /*access-order ordering*/ true) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        return size > capacity // remove oldest entry if size exceeds capacity
    }
}

println("--- Testing LRUCache ---")
val cache = LRUCache<String, String>(3)

println("Adding 1, 2, 3")
cache["1"] = "One"
cache["2"] = "Two"
cache["3"] = "Three"
println("Current cache: $cache") // Expected: {1=One, 2=Two, 3=Three}

println("Adding 4 (should evict 1)")
cache["4"] = "Four"
println("Current cache: $cache") // Expected: {2=Two, 3=Three, 4=Four}

println("Adding 5 (should evict 2)")
cache["5"] = "Five"
println("Current cache: $cache") // Expected: {3=Three, 4=Four, 5=Five}

println("Accessing 3 (3 becomes most recently used)")
println("Value for 3: " + cache["3"])
println("Current cache: $cache") // Expected: {4=Four, 5=Five, 3=Three}

println("Updating 2 (new entry, should evict 4 because 4 is now LRU)")
cache["2"] = "Updated"
println("Current cache: $cache") // Expected: {5=Five, 3=Three, 2=Updated}
