package Matrix

// count paths from top left to bottom right, 0's are blockages
// "memo" is for memoization - caches the already computed cells, runtime - O(n^2)
fun Array<IntArray>.paths(row: Int, col: Int, memo: Array<Array<Int>>): Int {
    if (this[row][col] == 1) return 0 // this path is blocked
    if (row == size - 1 || col == size - 1) return 1 // reached the end of a column or row
    if (memo[row][col] == 0) {
        memo[row][col] = paths(row + 1, col, memo) + paths(row, col + 1, memo)
    }
    return memo[row][col]
}

val matrix = arrayOf(
    intArrayOf(0,0,0),
    intArrayOf(0,1,0),
    intArrayOf(0,0,0)
)
println(matrix.paths(0,0, Array(matrix.size) { Array(matrix.size) { 0 } }))
