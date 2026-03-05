package arraysAndStrings

/**
 * Generates a list of squares for numbers from 0 up to n.
 *
 * Time Complexity: O(N) where N is the input number `n`.
 * Space Complexity: O(N) to store the list of squares.
 */
fun getSquaresUpTo(n: Int): List<Int> {
    return Array(n + 1) { it * it }.toList()
}

// Test the implementation
val n = 10
val result = getSquaresUpTo(n)
val expected = listOf(0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100)

println("Generating squares for numbers 0 to $n")
println("Result: $result")

if (result == expected) {
    println("PASS: Implementation matches expected output.")
} else {
    println("FAIL: Expected $expected, but got $result")
}
