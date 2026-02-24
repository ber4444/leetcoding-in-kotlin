package MorePatterns

// (arr + arr2).sorted() is O(N log N), whereas using two pointers is faster, O(N + M)

/**
 * Merges two sorted arrays into a single sorted array using the Two Pointers technique.
 *
 * Time Complexity: O(N + M)
 *   - We iterate through both arrays exactly once, where N is the size of the first array and M is the size of the second array.
 *
 * Space Complexity: O(N + M)
 *   - We create a new result array of size N + M to store the merged elements.
 */
fun merge(left: IntArray, right: IntArray): IntArray {
    val result = IntArray(left.size + right.size)
    var i = 0
    var j = 0
    var k = 0

    while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
            result[k++] = left[i++]
        } else {
            result[k++] = right[j++]
        }
    }

    while (i < left.size) {
        result[k++] = left[i++]
    }

    while (j < right.size) {
        result[k++] = right[j++]
    }

    return result
}

// Tests
val left1 = intArrayOf(1, 3, 5)
val right1 = intArrayOf(2, 4, 6)
val merged1 = merge(left1, right1)
println("Test 1 (Standard): ${merged1.joinToString()}")

val left2 = intArrayOf(1, 2, 3)
val right2 = intArrayOf()
val merged2 = merge(left2, right2)
println("Test 2 (Empty Right): ${merged2.joinToString()}")

val left3 = intArrayOf()
val right3 = intArrayOf(4, 5, 6)
val merged3 = merge(left3, right3)
println("Test 3 (Empty Left): ${merged3.joinToString()}")

val left4 = intArrayOf(1, 4, 7)
val right4 = intArrayOf(2, 3, 5, 6, 8)
val merged4 = merge(left4, right4)
println("Test 4 (Different Lengths): ${merged4.joinToString()}")

val left5 = intArrayOf(1, 3, 3)
val right5 = intArrayOf(2, 3, 4)
val merged5 = merge(left5, right5)
println("Test 5 (Duplicates): ${merged5.joinToString()}")
