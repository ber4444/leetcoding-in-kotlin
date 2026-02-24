package DynamicProgramming

// find all the pairs of two integers in an unsorted array
// that sum up to a given X

/*
 * Time Complexity: O(N)
 * We iterate through the array exactly once. Hash map operations (put, containsKey) take O(1) on average.
 * Thus, the algorithm runs in linear time.
 *
 * Space Complexity: O(N)
 * We use a HashMap to store the indices of the elements we have visited so far.
 * In the worst case, we store all elements in the map.
 */
fun twoSum(nums: Array<Int>, target: Int): List<Pair<Int, Int>> {
    val memo = HashMap<Int, Int>() // value -> index
    val result = mutableListOf<Pair<Int, Int>>()

    nums.forEachIndexed { index, num ->
        val complement = target - num
        if (memo.containsKey(complement)) {
            result.add(num to complement)
        }
        memo[num] = index
    }
    return result
}

// Tests
println("--- Test Case 1 ---")
val nums1 = arrayOf(3, 5, 2, -4, 8, 11)
val target1 = 7
println("Input: ${nums1.contentToString()}, Target: $target1")
println("Output: " + twoSum(nums1, target1))

println("\n--- Test Case 2 ---")
val nums2 = arrayOf(1, 2, 3, 9)
val target2 = 8
println("Input: ${nums2.contentToString()}, Target: $target2")
println("Output: " + twoSum(nums2, target2)) // Expected empty if no pair sums to 8 (wait, 3+5? 5 not there. 1+7? no. 2+6? no. 9-1=8? need sum. 1+7, 2+6, 3+5, 9-1? no. ) -> Empty.

println("\n--- Test Case 3 (Duplicates) ---")
val nums3 = arrayOf(3, 3)
val target3 = 6
println("Input: ${nums3.contentToString()}, Target: $target3")
println("Output: " + twoSum(nums3, target3))

println("\n--- Test Case 4 (No Result) ---")
val nums4 = arrayOf(1, 2, 3)
val target4 = 10
println("Input: ${nums4.contentToString()}, Target: $target4")
println("Output: " + twoSum(nums4, target4))