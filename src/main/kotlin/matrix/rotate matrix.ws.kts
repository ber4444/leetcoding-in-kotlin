package matrix

/**
 * Rotates an NxN matrix by 90 degrees clockwise in-place.
 *
 * Time Complexity: O(N^2) - We visit each element once.
 * Space Complexity: O(1) - We perform the rotation in-place without allocating extra space for the matrix.
 */
fun Array<IntArray>.rotate(): Array<IntArray> {
    if (isEmpty() || size != this[0].size) return this // Not a square
    val n = size
    for (layer in 0 until n / 2) {
        val last = n - 1 - layer
        for (i in layer until last) {
            val offset = i - layer
            val top = this[layer][i] // save top

            // left -> top
            this[layer][i] = this[last - offset][layer]

            // bottom -> left
            this[last - offset][layer] = this[last][last - offset]

            // right -> bottom
            this[last][last - offset] = this[i][last]

            // top -> right
            this[i][last] = top // right <- saved top
        }
    }
    return this
}

// Test Case 1: 3x3 Matrix
val matrix: Array<IntArray> = arrayOf(
    intArrayOf(2, 4, 6),
    intArrayOf(1, 3, 5),
    intArrayOf(9, 4, 3)
)
val expected1: Array<IntArray> = arrayOf(
    intArrayOf(9, 1, 2),
    intArrayOf(4, 3, 4),
    intArrayOf(3, 5, 6)
)
println("Test Case 1 (3x3): ${matrix.rotate() contentDeepEquals expected1}")

// Test Case 2: 2x2 Matrix
val matrix2: Array<IntArray> = arrayOf(
    intArrayOf(1, 2),
    intArrayOf(3, 4)
)
/*
 * 1 2
 * 3 4
 *
 * Rotated:
 * 3 1
 * 4 2
 */
val expected2: Array<IntArray> = arrayOf(
    intArrayOf(3, 1),
    intArrayOf(4, 2)
)
println("Test Case 2 (2x2): ${matrix2.rotate() contentDeepEquals expected2}")

// Test Case 3: 4x4 Matrix
val matrix3: Array<IntArray> = arrayOf(
    intArrayOf( 1,  2,  3,  4),
    intArrayOf( 5,  6,  7,  8),
    intArrayOf( 9, 10, 11, 12),
    intArrayOf(13, 14, 15, 16)
)
/*
 * 13  9  5  1
 * 14 10  6  2
 * 15 11  7  3
 * 16 12  8  4
 */
val expected4: Array<IntArray> = arrayOf(
    intArrayOf(13, 9, 5, 1),
    intArrayOf(14, 10, 6, 2),
    intArrayOf(15, 11, 7, 3),
    intArrayOf(16, 12, 8, 4)
)
println("Test Case 3 (4x4): ${matrix3.rotate() contentDeepEquals expected4}")