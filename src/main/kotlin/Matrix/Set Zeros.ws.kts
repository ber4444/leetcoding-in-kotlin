package Matrix

// given 2d matrix, if an element is 0, set the entire row and column to all 0
// must do it in place
fun Array<IntArray>.setZeros() {
    val rows = size
    val cols = this[0].size
    var shouldClearFirstCol = false

    for (row in 0 until rows) {
        if (this[row][0] == 0) shouldClearFirstCol = true
        for (col in 1 until cols) {
            if (this[row][col] == 0) {
                this[row][0] = 0
                this[0][col] = 0
            }
        }
    }

    // update based on the indicator
    for (row in 1 until rows) {
        for (col in 1 until cols) {
            if (this[row][0] == 0 || this[0][col] == 0) {
                this[row][col] = 0
            }
        }
    }

    // handle the first row
    if (this[0][0] == 0) {
        for (col in 0 until cols) this[0][col] = 0
    }

    // handle the first column
    if (shouldClearFirstCol) {
        for (row in 0 until rows) this[row][0] = 0
    }
}

val v = arrayOf(
    intArrayOf(1,1,1,1),
    intArrayOf(1,0,1,1),
    intArrayOf(1,1,0,1),
    intArrayOf(0,0,0,1))
v.setZeros()
println(v.map { it.toList() })