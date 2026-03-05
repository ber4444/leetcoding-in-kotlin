package matrix

/**
 * Multiplies two matrices.
 *
 * Time Complexity: O(aRows * bCols * aCols)
 * Where A is an (aRows x aCols) matrix and B is a (bRows x bCols) matrix.
 * This is effectively cubic time O(N^3) for square matrices of size N.
 *
 * Space Complexity: O(aRows * bCols)
 * Space required to store the result matrix.
 */
fun multiply(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
    val aRows = a.size
    val aCols = a[0].size
    val bRows = b.size
    val bCols = b[0].size

    if (aCols != bRows) throw IllegalArgumentException("Illegal matrix dimensions: cols of A ($aCols) must equal rows of B ($bRows).")

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

// Test Case 1: Provided example
println("--- Test Case 1: Provided Example ---")
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

// Test Case 2: Identity Matrix
println("\n--- Test Case 2: Identity Matrix ---")
val identity = arrayOf(
    doubleArrayOf(1.0, 0.0, 0.0),
    doubleArrayOf(0.0, 1.0, 0.0),
    doubleArrayOf(0.0, 0.0, 1.0)
)
// Multiplying by identity should return the same matrix
val resultIdentity = multiply(matrix, identity)
println("Matches original: ${resultIdentity.contentDeepEquals(matrix) || resultIdentity.contentDeepToString() == matrix.contentDeepToString()}")
// Note: floating point comparison might be tricky with exact equality, but 1.0 and 0.0 are usually exact.
// Let's print the result to be sure.
println(resultIdentity.contentDeepToString())

// Test Case 3: Rectangular Matrices
println("\n--- Test Case 3: Rectangular Matrices ---")
// 2x3 matrix
val rectA = arrayOf(
    doubleArrayOf(1.0, 2.0, 3.0),
    doubleArrayOf(4.0, 5.0, 6.0)
)
// 3x2 matrix
val rectB = arrayOf(
    doubleArrayOf(7.0, 8.0),
    doubleArrayOf(9.0, 1.0),
    doubleArrayOf(2.0, 3.0)
)

// Result should be 2x2
// [1*7 + 2*9 + 3*2, 1*8 + 2*1 + 3*3] = [7+18+6, 8+2+9] = [31, 19]
// [4*7 + 5*9 + 6*2, 4*8 + 5*1 + 6*3] = [28+45+12, 32+5+18] = [85, 55]
val rectResult = multiply(rectA, rectB)
println("2x3 * 3x2 Result (Should be [[31.0, 19.0], [85.0, 55.0]]):")
println(rectResult.contentDeepToString())

// Test Case 4: Dimension Mismatch
println("\n--- Test Case 4: Dimension Mismatch ---")
try {
    val invalidB = arrayOf(
        doubleArrayOf(1.0, 2.0),
        doubleArrayOf(3.0, 4.0)
    )
    // rectA is 2x3, invalidB is 2x2. Cols(A)=3 != Rows(B)=2.
    multiply(rectA, invalidB)
} catch (e: IllegalArgumentException) {
    println("SUCCESS: Caught expected exception: ${e.message}")
}
