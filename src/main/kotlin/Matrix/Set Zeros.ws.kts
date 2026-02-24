package Matrix

/**
 * given 2d matrix, if an element is 0, set the entire row and column to all 0
 * Time Complexity: O(M * N) where M is the number of rows and N is the number of columns.
 * We iterate through the matrix twice.
 * Space Complexity: O(1) in-place. We use the first row and first column as markers to store the state.
 */
fun Array<IntArray>.setZeros() {
    val rows = size
    if (rows == 0) return
    val cols = this[0].size
    var shouldClearFirstCol = false

    // First pass: mark zeros on first row and first column
    for (row in 0 until rows) {
        // Check if the first column element is 0
        if (this[row][0] == 0) shouldClearFirstCol = true
        
        // Check the rest of the row
        for (col in 1 until cols) {
            if (this[row][col] == 0) {
                this[row][0] = 0
                this[0][col] = 0
            }
        }
    }

    // Second pass: use the markers to set elements to 0
    // Start from 1 to avoid interfering with the first row/col flags immediately
    for (row in 1 until rows) {
        for (col in 1 until cols) {
            if (this[row][0] == 0 || this[0][col] == 0) {
                this[row][col] = 0
            }
        }
    }

    // Handle the first row
    // Check if the first element of the first row (which tracks the first row) is 0
    if (this[0][0] == 0) {
        for (col in 0 until cols) this[0][col] = 0
    }

    // Handle the first column
    if (shouldClearFirstCol) {
        for (row in 0 until rows) this[row][0] = 0
    }
}

fun printMatrix(matrix: Array<IntArray>) {
    matrix.forEach { row ->
        println(row.joinToString(", "))
    }
    println("---")
}

// Test Case 1: Standard case
println("Test Case 1:")
val v1 = arrayOf(
    intArrayOf(1, 1, 1, 1),
    intArrayOf(1, 0, 1, 1),
    intArrayOf(1, 1, 0, 1),
    intArrayOf(0, 0, 0, 1)
)
v1.setZeros()
printMatrix(v1)

// Test Case 2: Zero at (0,0)
println("Test Case 2: Zero at (0,0)")
val v2 = arrayOf(
    intArrayOf(0, 1, 2),
    intArrayOf(3, 4, 5),
    intArrayOf(6, 7, 8)
)
v2.setZeros()
printMatrix(v2)

// Test Case 3: Zero in first row only
println("Test Case 3: Zero in first row")
val v3 = arrayOf(
    intArrayOf(1, 0, 1),
    intArrayOf(1, 1, 1),
    intArrayOf(1, 1, 1)
)
v3.setZeros()
printMatrix(v3)

// Test Case 4: Zero in first column only
println("Test Case 4: Zero in first column")
val v4 = arrayOf(
    intArrayOf(1, 1, 1),
    intArrayOf(0, 1, 1),
    intArrayOf(1, 1, 1)
)
v4.setZeros()
printMatrix(v4)
