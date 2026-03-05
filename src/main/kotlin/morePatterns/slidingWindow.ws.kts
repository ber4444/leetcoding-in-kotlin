package morePatterns

// sliding window also applies to these kinds of problems:
// - max sum of any contiguous subarray of size k
// - longest substring with k distinct chars
// - smallest subarray with sum greater than or equal to s

/**
Given the height of a staircase and the max number of steps you can take at any one time,
calculate the number of ways you can climb a staircase.

height = 4
maxSteps = 2
output = 5
// 1, 1, 1, 1
// 1, 1, 2
// 1, 2, 1
// 2, 1, 1
// 2, 2

Time Complexity: O(h) where h is the height of the staircase
Space Complexity: O(h) where h is the height of the staircase
 */
fun staircaseTraversal(height: Int, maxSteps: Int): Int {
    var currentNumberOfWays = 0
    val waysToTop = mutableListOf(1)

    // height + 1 because we must account for 0th step, which can be traversed
    // in exactly 1 step
    for (currentHeight in 1 until height + 1) {
        // Window size is maxSteps
        val startOfWindow = currentHeight - maxSteps - 1
        val endOfWindow = currentHeight - 1
        // Remove the value from the start of the window
        if (startOfWindow >= 0) {
            currentNumberOfWays -= waysToTop[startOfWindow]
        }
        // Add the value at the end of the window
        currentNumberOfWays += waysToTop[endOfWindow]

        waysToTop.add(currentNumberOfWays)
    }

    return waysToTop[height]
}

// Tests
println("Height: 4, MaxSteps: 2 -> ${staircaseTraversal(4, 2)}")
println("Height: 3, MaxSteps: 2 -> ${staircaseTraversal(3, 2)}")
println("Height: 1, MaxSteps: 2 -> ${staircaseTraversal(1, 2)}")
println("Height: 5, MaxSteps: 2 -> ${staircaseTraversal(5, 2)}")
