package dynamicProgramming

/**
 * Minimum operation required to transform one string to another (Levenshtein distance).
 *
 * Time Complexity: O(N * M) where N and M are the lengths of the two strings.
 * Space Complexity: O(N * M) for the 2D array.
 */
fun String.editDistance(s: String): Int {
    val matrix = Array(length + 1) { Array(s.length + 1) { 0 } }
    for (i in 0..length) matrix[i][0] = i
    for (i in 0..s.length) matrix[0][i] = i

    for (i in indices) {
        for (j in s.indices) {
            matrix[i + 1][j + 1] = if (this[i] == s[j]) {
                matrix[i][j]
            } else {
                1 + minOf(matrix[i][j], matrix[i][j + 1], matrix[i + 1][j])
            }
        }
    }

    return matrix[length][s.length]
}

// Tests
println("sunday".editDistance("monday") == 2)
println("horse".editDistance("ros") == 3)
println("intention".editDistance("execution") == 5)
println("".editDistance("a") == 1)
println("abc".editDistance("abc") == 0)