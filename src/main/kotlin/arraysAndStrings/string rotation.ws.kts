package arraysAndStrings

/**
 * Checks if a string is a rotation of another string.
 *
 * Algorithm:
 * 1. Check if lengths are equal and not empty.
 * 2. Concatenate `this` with `this`.
 * 3. Check if `s` is a substring of `this + this`.
 *
 * Time Complexity: O(N) average case, where N is the length of the string.
 * Note: Java's `indexOf` uses naive string search which can be O(N*M) worst case (O(N^2) here).
 * Space Complexity: O(N) due to creating a new string of length 2N.
 */
fun String.isRotation(s: String): Boolean =
    length == s.length && isNotEmpty() && (this + this).indexOf(s) >= 0

// Test cases
println("Testing 'apple' vs 'pleap': " + "apple".isRotation("pleap")) // expected: true
println("Testing 'waterbottle' vs 'erbottlewat': " + "waterbottle".isRotation("erbottlewat")) // expected: true
println("Testing 'camera' vs 'macera': " + "camera".isRotation("macera")) // expected: false
println("Testing 'abc' vs 'bca': " + "abc".isRotation("bca")) // expected: true
println("Testing 'abc' vs 'cba': " + "abc".isRotation("cba")) // expected: false (reverse is not rotation)
println("Testing 'a' vs 'a': " + "a".isRotation("a")) // expected: true
println("Testing empty string: " + "".isRotation("")) // expected: false
println("Testing different lengths 'ab' vs 'abc': " + "ab".isRotation("abc")) // expected: false
