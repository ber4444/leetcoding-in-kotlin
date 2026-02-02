package Matrix

fun multiply(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
    val aRows = a.size
    val aCols = a[0].size
    val bRows = b.size
    val bCols = b[0].size

    if (aCols != bRows) throw IllegalArgumentException("Illegal matrix dimensions.")

    val result = Array(aRows) { DoubleArray(bCols) }

    for (i in 0 until aRows) {
        for (j in 0 until bCols) {
            for (k in 0 until aCols) {
                result[i][j] += a[i][k] * b[k][j]
            }
        }
    }

    return result
}

val matrix = arrayOf(
    doubleArrayOf(1.2, 2.5, 3.4),
    doubleArrayOf(1.2, 4.5, 5.46),
    doubleArrayOf(12.2, 23.5, 3.45)
)

val matrix2 = arrayOf(
    doubleArrayOf(11.22, 223.5, 43.4),
    doubleArrayOf(13.2, 42.5, 53.46),
    doubleArrayOf(124.2, 23.5, 34.45)
)

println(multiply(matrix, matrix2).contentDeepToString())
