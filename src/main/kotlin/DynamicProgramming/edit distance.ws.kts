package DynamicProgramming

// minimum operation required to transform one thing to another
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

println("sunday".editDistance("monday") == 2)