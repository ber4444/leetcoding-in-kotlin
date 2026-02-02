package ArraysAndStringsCtCi

// given MxN matrix, for all 0 elements, set its entire column and row to 0
// the O(MN) space solution is straightforward, this one is O(1) space:
// (the 1st col and 1st row is used to keep track of all rows and cols with 0's)
fun Array<IntArray>.setZeros(): Array<IntArray> {
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

val matrix: Array<IntArray> = arrayOf(intArrayOf(2, 4, 6, 0), intArrayOf(1, 3, 5, 4), intArrayOf(9, 4, 3, 2))
val zeroed: Array<IntArray> = arrayOf(intArrayOf(0, 0, 0, 0), intArrayOf(1, 3, 5, 0), intArrayOf(9, 4, 3, 0))

matrix.setZeros() contentDeepEquals zeroed
