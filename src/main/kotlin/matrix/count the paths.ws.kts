package matrix

/**
 * Count paths from top left (0,0) to bottom right (n-1, m-1).
 * 1's are blockages, 0's are valid paths.
 *
 * Time Complexity: O(N * M) where N is number of rows and M is number of columns.
 *   Each cell is computed at most once due to memoization.
 * Space Complexity: O(N * M)
 *   O(N * M) for the memoization table.
 *   O(N + M) for the recursion stack depth.
 */
fun Array<IntArray>.paths(row: Int, col: Int, memo: Array<IntArray>): Int {
    // Base case: Out of bounds or blocked
    if (row >= size || col >= this[0].size || this[row][col] == 1) return 0
    
    // Base case: Reached destination
    if (row == size - 1 && col == this[0].size - 1) return 1
    
    // Return cached value if already computed
    // Using -1 to represent uncomputed state
    if (memo[row][col] != -1) return memo[row][col]
    
    // Recursive step: sum of paths from right and down
    memo[row][col] = paths(row + 1, col, memo) + paths(row, col + 1, memo)
    return memo[row][col]
}

// Helper to run test
fun test(name: String, matrix: Array<IntArray>, expected: Int) {
    // Initialize memo with -1
    val memo = Array(matrix.size) { IntArray(matrix[0].size) { -1 } }
    val result = matrix.paths(0, 0, memo)
    if (result == expected) {
        println("$name: PASS (Result: $result)")
    } else {
        println("$name: FAIL (Expected $expected, got $result)")
    }
}

// Test cases

// Test 1: Original example
val matrix1 = arrayOf(
    intArrayOf(0,0,0),
    intArrayOf(0,1,0),
    intArrayOf(0,0,0)
)
test("Original Example", matrix1, 2)

// Test 2: Destination blocked
val matrixBlocked = arrayOf(
    intArrayOf(0,0),
    intArrayOf(0,1)
)
test("Destination Blocked", matrixBlocked, 0)

// Test 3: Open 2x2 matrix
val matrixOpen = arrayOf(
    intArrayOf(0,0),
    intArrayOf(0,0)
)
test("2x2 Open", matrixOpen, 2)

// Test 4: Larger matrix with blockages
val matrixLarge = arrayOf(
    intArrayOf(0,0,0,0),
    intArrayOf(0,1,1,0),
    intArrayOf(0,0,0,0)
)
// Path must go around the blockages (1,1) and (1,2)
// (0,0)->(1,0)->(2,0)->(2,1)->(2,2)->(2,3)->(1,3) NO can't go up.
// (0,0)->(0,1)->(0,2)->(0,3)->(1,3)->(2,3). 1 path.
// (0,0)->(1,0)->(2,0)->(2,1)->(2,2)->(2,3). 1 path.
// Total 2.
test("3x4 with blockages", matrixLarge, 2)
