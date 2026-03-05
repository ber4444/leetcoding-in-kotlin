package matrix

/**
 * Rotates an N x N matrix 90 degrees clockwise.
 *
 * Time Complexity: O(N^2) where N is the number of rows/columns. We visit each cell twice.
 * Space Complexity: O(1) as we perform the rotation in-place.
 */
fun rotate(matrix: Array<IntArray>) {
    transpose(matrix)
    reflect(matrix)
}

// swap each entry about the diagonal
private fun transpose(matrix: Array<IntArray>) {
    val length = matrix.size

    for (row in 0 until length) {
        // Note that we start iterating through columns at position == row
        // This is because we are working diagonally
        for (column in row until length) {
            val temp = matrix[row][column]
            matrix[row][column] = matrix[column][row]
            matrix[column][row] = temp
        }
    }
}

// reverse the array from left to right
private fun reflect(matrix: Array<IntArray>) {
    val length = matrix.size

    for (row in 0 until length) {
        // Note that we reflect until length / 2
        // No reason to keep going after halfway
        for (column in 0 until length / 2) {
            val temp = matrix[row][column]
            matrix[row][column] = matrix[row][length - column - 1]
            matrix[row][length - column - 1] = temp
        }
    }
}

// Test implementation
val matrix = arrayOf(
    intArrayOf(1, 2, 3),
    intArrayOf(4, 5, 6),
    intArrayOf(7, 8, 9)
)

println("Original Matrix:")
matrix.forEach { println(it.contentToString()) }

rotate(matrix)

println("\nRotated Matrix:")
matrix.forEach { println(it.contentToString()) }