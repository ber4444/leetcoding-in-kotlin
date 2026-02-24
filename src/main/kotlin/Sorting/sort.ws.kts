package Sorting

// note: Collections.sort uses merge sort, while Arrays.sort uses quick sort (the latter is also O(n log n) but has worse case of O(n^2), space complexity is O(log n))
// using heap sort, you can get O(1) space complexity with the same O(n log n) time complexity
// forget about bubble sort, insertion sort, selection sort, radix sort, they are SLOW
// counting sort is FAST

/**
 * Sorts the IntArray using Merge Sort algorithm.
 *
 * Time Complexity: O(n log n), it's divide and conquer
 * Space Complexity: O(n) due to temporary arrays in merge
 *
 * @param start The starting index of the range to sort.
 * @param end The ending index of the range to sort.
 */
fun IntArray.sort(start: Int, end: Int): IntArray {
    if (start < end) {
        val middle = (start + end) / 2
        sort(start, middle) // sort left
        sort(middle + 1, end) // sort right
        merge(start, middle, end) // merge them back together
    }
    return this
}

fun IntArray.merge(start: Int, middle: Int, end: Int) {
    // Copying ranges creates new arrays, contributing to O(n) space complexity
    val left = copyOfRange(start, middle + 1)
    val right = copyOfRange(middle + 1, end + 1)

    var leftIndex = 0
    var rightIndex = 0

    for (i in start..end) {
        // If left has elements AND (right is empty OR left element is smaller/equal)
        // This ensures stability if we were sorting objects (though for Ints it doesn't matter much)
        if (leftIndex < left.size && (rightIndex >= right.size || left[leftIndex] <= right[rightIndex])) {
            this[i] = left[leftIndex]
            leftIndex++
        } else {
            this[i] = right[rightIndex]
            rightIndex++
        }
    }
}

// --- Test Suite ---

fun runTest(name: String, input: IntArray) {
    val expected = input.sorted().toIntArray()
    val subject = input.copyOf()
    
    if (subject.isNotEmpty()) {
        subject.sort(0, subject.size - 1)
    }

    if (subject.contentEquals(expected)) {
        println("✅ $name: PASS")
    } else {
        println("❌ $name: FAIL")
        println("   Input:    ${input.contentToString()}")
        println("   Expected: ${expected.contentToString()}")
        println("   Actual:   ${subject.contentToString()}")
    }
}

println("--- Running Merge Sort Tests ---")

runTest("Standard Case", intArrayOf(13, 14, 15, 16, 5))
runTest("Already Sorted", intArrayOf(1, 2, 3, 4, 5))
runTest("Reverse Sorted", intArrayOf(5, 4, 3, 2, 1))
runTest("With Duplicates", intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5))
runTest("Single Element", intArrayOf(42))
runTest("Empty Array", intArrayOf())
runTest("Negative Numbers", intArrayOf(-5, -1, -10, 0, 5))
runTest("Large Range", intArrayOf(Int.MAX_VALUE, 0, Int.MIN_VALUE))

// Original manual test
val v = intArrayOf(13,14,15,16,5)
val v2 = v.copyOf().sorted().toIntArray()
v.sort(0,v.size-1)
println("\nOriginal manual check: ${v.contentEquals(v2)}")
