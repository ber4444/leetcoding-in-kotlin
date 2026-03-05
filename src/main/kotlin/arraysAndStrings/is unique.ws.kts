package arraysAndStrings

// Helper function to test implementations
fun check(name: String, result: Boolean) {
    println("$name: ${if (result) "PASS" else "FAIL"}")
}

// -------------------------------------------------------
// V1: Using a Set
// -------------------------------------------------------
// Time Complexity: O(N)
//   - We iterate over the string to populate the set.
// Space Complexity: O(N) (or O(1) if character set is fixed)
//   - We store unique characters in a Set.
fun String.isUnique() = length == toSet().size

println("--- Testing V1 (Set) ---")
check("Empty string", "".isUnique())
check("Unique chars", "abcd".isUnique())
check("Duplicate chars", !"test".isUnique())
check("Duplicate chars end", !"estt".isUnique())


// -------------------------------------------------------
// V2: Using a Boolean Array (Assuming ASCII)
// -------------------------------------------------------
// Time Complexity: O(N)
//   - We iterate through the string once.
//   - Optimization: If N > 128, we return immediately, so effective time is O(min(N, 128)) => O(1).
// Space Complexity: O(1)
//   - We use a fixed size boolean array of 128 elements.
fun String.isUniqueV2(): Boolean {
	if (length > 128) return false // you cannot form a unique str from a larger array of chrs than what's in the alphabet

	val seen = BooleanArray(128) // the flag at index i indicates whether chr i in the alphabet is in the str
	for (char in this) {
		val code = char.code // Int value of the Char
		if (seen[code]) return false // already found this chr in the str
		seen[code] = true
	}
	return true
}

println("\n--- Testing V2 (Boolean Array) ---")
check("Empty string", "".isUniqueV2())
check("Unique chars", "abcd".isUniqueV2())
check("Duplicate chars", !"test".isUniqueV2())
check("Duplicate chars end", !"estt".isUniqueV2())


// -------------------------------------------------------
// V3: No Data Structures (Brute Force)
// -------------------------------------------------------
// Time Complexity: O(N^2)
//   - Nested loops compare each character with every other character.
// Space Complexity: O(1)
//   - No additional data structures used.
fun String.isUniqueV3(): Boolean {
	for (i in indices) {
		for (j in i + 1 until length) {
			if (this[i] == this[j]) return false
		}
	}
	return true
}

println("\n--- Testing V3 (No Data Structures) ---")
check("Empty string", "".isUniqueV3())
check("Unique chars", "abcd".isUniqueV3())
check("Duplicate chars", !"test".isUniqueV3())
check("Duplicate chars end", !"estt".isUniqueV3())
