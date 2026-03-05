package arraysAndStrings

/**
 * Given an array of integers nums and an integer target,
 * return indices of the two numbers such that they add up to target.
 *
 * You may assume that each input would have exactly one solution, and
 * you may not use the same element twice.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
fun twoSum(nums: IntArray, target: Int): IntArray {
    val seen = mutableMapOf<Int, Int>() // maps numbers in the array to their index
    for ((i, num) in nums.withIndex()) { // gets index and value at the same time
        val complement = target - num
        seen[complement]?.let { j -> // if we already saw the complement, return its index and the current index
            return intArrayOf(j, i)
        }
        seen[num] = i
    }
    return IntArray(0) // O(n)
}

println(twoSum(intArrayOf(2, 7, 11, 15), 9).contentEquals(intArrayOf(0, 1)))
println(twoSum(intArrayOf(3, 2, 4), 6).contentEquals(intArrayOf(1, 2)))
println(twoSum(intArrayOf(3, 3), 6).contentEquals(intArrayOf(0, 1)))
