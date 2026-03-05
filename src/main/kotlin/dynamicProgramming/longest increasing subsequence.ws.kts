package dynamicProgramming

/**
 * Longest Increasing Subsequence
 *
 * Time Complexity: O(N^2)
 * Space Complexity: O(N)
 */
fun lengthOfLIS(nums: IntArray): Int {
    if (nums.isEmpty()) return 0
    // dp[i] represents the length of the longest increasing subsequence that ends with the element at index i
    // Initialize all to 1 - base case
    val dp = IntArray(nums.size) { 1 }

    for (i in 1 until nums.size) {
        // All elements before i
        for (j in 0 until i) {
            if (nums[j] < nums[i]) {
                dp[i] = dp[i].coerceAtLeast(dp[j] + 1)
            }
        }
    }

    return dp.maxOrNull() ?: 0
}

// Test cases
val test1 = intArrayOf(10, 9, 2, 5, 3, 7, 101, 18)
val result1 = lengthOfLIS(test1)
println("Input: ${test1.contentToString()}, LIS: $result1 (Expected: 4)")

val test2 = intArrayOf(0, 1, 0, 3, 2, 3)
val result2 = lengthOfLIS(test2)
println("Input: ${test2.contentToString()}, LIS: $result2 (Expected: 4)")

val test3 = intArrayOf(7, 7, 7, 7, 7, 7, 7)
val result3 = lengthOfLIS(test3)
println("Input: ${test3.contentToString()}, LIS: $result3 (Expected: 1)")

val test4 = intArrayOf()
val result4 = lengthOfLIS(test4)
println("Input: ${test4.contentToString()}, LIS: $result4 (Expected: 0)")
