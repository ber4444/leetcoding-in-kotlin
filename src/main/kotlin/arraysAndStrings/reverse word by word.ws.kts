package arraysAndStrings

/**
 * Time Complexity: O(N) where N is the length of the string.
 * - split: O(N)
 * - reversed: O(N)
 * - joinToString: O(N)
 *
 * Space Complexity: O(N) to store the list of words and the new string.
 */
fun String.reverseWords(): String {
    // split by one or more spaces and remove empty strings caused by leading/trailing spaces if not trimmed,
    // or use trim() then split by regex
    return this.trim().split("\\s+".toRegex()).reversed().joinToString(" ")
}

// Tests
val test1 = "hello world!".reverseWords() == "world! hello"
val test2 = "the sky is blue".reverseWords() == "blue is sky the"
// Test handling of leading/trailing spaces and multiple spaces
val test3 = "  hello world  ".reverseWords() == "world hello"
val test4 = "a good   example".reverseWords() == "example good a"

println("Test 1 (Basic): $test1")
println("Test 2 (Sentence): $test2")
println("Test 3 (Trim spaces): $test3")
println("Test 4 (Multiple spaces): $test4")