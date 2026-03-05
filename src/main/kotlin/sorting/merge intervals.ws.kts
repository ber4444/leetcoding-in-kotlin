package sorting

import java.util.LinkedList

/**
 * Given an array of intervals, merge all overlapping intervals,
 * and return an array of the non-overlapping intervals that cover
 * all the intervals in the input.
 *
 * Time Complexity:  O(n log n) — dominated by sorting the input array.
 *                   The subsequent single-pass loop runs in O(n).
 *
 * Space Complexity: O(n) — the `merged` LinkedList holds at most n intervals
 *                   in the worst case (no overlaps). The sort may also use
 *                   O(log n) stack space internally, but O(n) dominates.
 */
fun merge(intervals: Array<IntArray>): Array<IntArray> {
    // Sort the inputs by the first value
    intervals.sortBy { it.first() }

    val merged = LinkedList<IntArray>()

    for (array in intervals) {
        // if the list of merged intervals is empty or if the current
        // interval does not overlap with the previous, simply append it.
        if (merged.isEmpty() || merged.last()[1] < array.first()) {
            merged.add(array)
        } else {
            // otherwise, there is overlap, so we merge the current and previous
            // intervals.
            merged.last()[1] = merged.last()[1].coerceAtLeast(array[1])
        }
    }

    return merged.toTypedArray()
}

fun test(label: String, intervals: Array<IntArray>, expected: Array<IntArray>) {
    val result = merge(intervals)
    val pass = result.size == expected.size &&
            result.zip(expected).all { (r, e) -> r.contentEquals(e) }
    println("$label: ${if (pass) "PASS ✅" else "FAIL ❌"}")
    println("  Input:    ${intervals.map { it.toList() }}")
    println("  Output:   ${result.map { it.toList() }}")
    println("  Expected: ${expected.map { it.toList() }}\n")
}

// Example 1 — [1,3] and [2,6] overlap → merged into [1,6]
test(
    "Example 1",
    arrayOf(intArrayOf(1,3), intArrayOf(2,6), intArrayOf(8,10), intArrayOf(15,18)),
    arrayOf(intArrayOf(1,6), intArrayOf(8,10), intArrayOf(15,18))
)

// Example 2 — [1,4] and [4,5] share a boundary → merged into [1,5]
test(
    "Example 2",
    arrayOf(intArrayOf(1,4), intArrayOf(4,5)),
    arrayOf(intArrayOf(1,5))
)

// Example 3 — unsorted input; [4,7] and [1,4] overlap after sort → [1,7]
test(
    "Example 3",
    arrayOf(intArrayOf(4,7), intArrayOf(1,4)),
    arrayOf(intArrayOf(1,7))
)