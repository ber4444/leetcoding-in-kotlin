package matrix

// given MxN matrix, for all 0 elements, set its entire column and row to 0
// Time Complexity: O(M * N)
// Space Complexity: O(1)
fun Array<IntArray>.setZeros(): Array<IntArray> {
    if (isEmpty()) return this
    
    // store whether or not there are 0's in the first col and first row
    val rowHasZero = this[0].any { it == 0 }
    val colHasZero = indices.any { this[it][0] == 0 }

    // Check for zeros in the rest of the array
    for (i in 1 until size) {
        for (j in 1 until this[0].size) {
            if (this[i][j] == 0) {
                this[i][0] = 0
                this[0][j] = 0
            }
        }
    }

    // Nullify rows based on values in first column
    for (i in 1 until size) {
        if (this[i][0] == 0) {
            nullifyRow(this, i)
        }
    }

    // Nullify columns based on values in first row
    for (j in 1 until this[0].size) {
        if (this[0][j] == 0) {
            nullifyColumn(this, j)
        }
    }

    // Nullify first row
    if (rowHasZero) {
        nullifyRow(this, 0)
    }

    // Nullify first column
    if (colHasZero) {
        nullifyColumn(this, 0)
    }
    return this
}

fun nullifyRow(matrix: Array<IntArray>, row: Int) {
    for (j in matrix[0].indices) {
        matrix[row][j] = 0
    }
}

fun nullifyColumn(matrix: Array<IntArray>, col: Int) {
    for (i in matrix.indices) {
        matrix[i][col] = 0
    }
}

// Tests
val matrix: Array<IntArray> = arrayOf(intArrayOf(2, 4, 6, 0), intArrayOf(1, 3, 5, 4), intArrayOf(9, 4, 3, 2))
val zeroed: Array<IntArray> = arrayOf(intArrayOf(0, 0, 0, 0), intArrayOf(1, 3, 5, 0), intArrayOf(9, 4, 3, 0))

val test1 = matrix.setZeros() contentDeepEquals zeroed

val matrix2 = arrayOf(intArrayOf(0, 1, 2, 0), intArrayOf(3, 4, 5, 2), intArrayOf(1, 3, 1, 5))
val zeroed2 = arrayOf(intArrayOf(0, 0, 0, 0), intArrayOf(0, 4, 5, 0), intArrayOf(0, 3, 1, 0))
val test2 = matrix2.setZeros() contentDeepEquals zeroed2

val matrix3 = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9))
val zeroed3 = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9))
val test3 = matrix3.setZeros() contentDeepEquals zeroed3

val matrix4 = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 0, 6), intArrayOf(7, 8, 9))
val zeroed4 = arrayOf(intArrayOf(1, 0, 3), intArrayOf(0, 0, 0), intArrayOf(7, 0, 9))
val test4 = matrix4.setZeros() contentDeepEquals zeroed4

"Tests: test1=$test1, test2=$test2, test3=$test3, test4=$test4"
