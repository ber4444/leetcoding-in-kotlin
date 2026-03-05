package arraysAndStrings

// assume the array is unsorted
// Time Complexity: O(N log N) due to sorting
// Space Complexity: O(log N) due to recursion stack used by the sort algorithm
fun IntArray.findKthLargest(k: Int): Int {
    sort()
    return this[size - k]
}

println(intArrayOf(2, 3, 1, 4, 5).findKthLargest(2)) // Expected: 4
println(intArrayOf(3, 2, 1, 5, 6, 4).findKthLargest(2)) // Expected: 5
println(intArrayOf(3, 2, 3, 1, 2, 4, 5, 5, 6).findKthLargest(4)) // Expected: 4