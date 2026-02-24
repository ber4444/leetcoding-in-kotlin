package ArraysAndStrings

/**
 * Checks if a string is a permutation of a palindrome.
 *
 * Time Complexity: O(N) where N is the length of the string.
 * - Regex replacement: O(N)
 * - Grouping and counting: O(N)
 * - Counting odds: O(1) (bounded by alphabet size)
 *
 * Space Complexity: O(N)
 * - Creates a new string for the regex replacement.
 * - Map storage is O(1) (bounded by alphabet size).
 */
fun String.isPalindromePerm(): Boolean {
    // count how many times each chr appears (excluding non-letters)
    val charCounts = replace("[^A-Za-z]".toRegex(), "").groupingBy { it }.eachCount()

    // no more than 1 chr should have an odd count
    return charCounts.count { it.value % 2 == 1 } <= 1
}

// Test cases
val testCases = listOf(
    "pdepdrremama" to true,
    "pedram" to false,
    "ped ram" to false,
    "pde  pd6rr--emam%a" to true,
    "aba" to true,
    "aab" to true,
    "baa" to true,
    "abc" to false
)

println("Running tests...")
testCases.forEach { (input, expected) ->
    val result = input.isPalindromePerm()
    if (result == expected) {
        println("PASS: \"$input\" -> $result")
    } else {
        println("FAIL: \"$input\" -> $result, expected $expected")
    }
}
