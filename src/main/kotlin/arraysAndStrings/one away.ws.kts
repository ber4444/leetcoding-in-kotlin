package arraysAndStrings

import kotlin.math.abs

/**
 * Checks if two strings are one edit (insert, remove, or replace) away from each other.
 *
 * Time Complexity: O(n) where n is the length of the shorter string.
 * Space Complexity: O(1) as we only use a constant amount of extra space.
 */
fun String.oneAway(str: String): Boolean {
    // If the length difference is greater than 1, they can't be one edit away
    if (abs(length - str.length) > 1) return false

    // Identify which string is shorter and which is longer
    val (shorterStr, longerStr) = if (length < str.length) this to str else str to this

    var foundDifference = false
    var idx1 = 0 // Index for shorterStr
    var idx2 = 0 // Index for longerStr

    while (idx1 < shorterStr.length && idx2 < longerStr.length) {
        if (shorterStr[idx1] != longerStr[idx2]) {
            // If we've already found a difference, the strings are not one away
            if (foundDifference) return false
            foundDifference = true

            // If lengths are equal (replace case), increment short index as well
            if (shorterStr.length == longerStr.length) idx1++
        } else {
            // If characters match, move short index
            idx1++
        }
        // Always move long index
        idx2++
    }
    return true
}

// Test cases
val testCases = listOf(
    ("pedram" to "pedram") to true,
    ("pedram" to "edram") to true,
    ("pedram" to "peram") to true,
    ("pedram" to "pedra") to true,
    ("pedram" to "nedram") to true,
    ("pedram" to "pedpam") to true,
    ("pedram" to "pedran") to true,
    ("ppeedram" to "pedram") to false,
    ("ppedram" to "pedran") to false,
    ("pedram" to "pedrann") to false,
    ("pale" to "ple") to true,
    ("pales" to "pale") to true,
    ("pale" to "bale") to true,
    ("pale" to "bake") to false
)

println("Running tests...")
var allPassed = true
testCases.forEach { (input, expected) ->
    val (s1, s2) = input
    val result = s1.oneAway(s2)
    if (result != expected) {
        println("FAIL: \"$s1\".oneAway(\"$s2\") -> $result (Expected: $expected)")
        allPassed = false
    } else {
        // println("PASS: \"$s1\" vs \"$s2\"")
    }
}

if (allPassed) {
    println("All tests passed!")
} else {
    println("Some tests failed.")
}
