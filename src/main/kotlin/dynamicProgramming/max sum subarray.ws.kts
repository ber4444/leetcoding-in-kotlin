package dynamicProgramming

// find subarray with largest sum, return the sum
// Time Complexity: O(n) where n is the size of the array, as we iterate through the array once.
// Space Complexity: O(1) as we only use a few variables for storage.
fun IntArray.maxSumSubarray(): Int {
    if (isEmpty()) return 0
    var maxEnding = this[0]
    var maxSoFar = this[0]

    for (i in 1 until size) {
        maxEnding = maxOf(this[i], maxEnding + this[i])
        maxSoFar = maxOf(maxSoFar, maxEnding)
    }

    return maxSoFar
}

// Test cases
val v = intArrayOf(5, 2, -1, 3, -4, -2)
println("Test 1 (Mixed): " + (v.maxSumSubarray() == 9))

val v2 = intArrayOf(-2, 1, -3, 4, -1, 2, 1, -5, 4)
println("Test 2 (Kadane's example): " + (v2.maxSumSubarray() == 6))

val v3 = intArrayOf(1)
println("Test 3 (Single element): " + (v3.maxSumSubarray() == 1))

val v4 = intArrayOf(5, 4, -1, 7, 8)
println("Test 4 (Mostly positive): " + (v4.maxSumSubarray() == 23))

val v5 = intArrayOf(-1, -2, -3)
println("Test 5 (All negative): " + (v5.maxSumSubarray() == -1))
