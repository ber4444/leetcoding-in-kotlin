package ArraysAndStrings

// Check if str1 is permutation of str2
// Clarify if the permutation comparison is case sensitive, and if white space counts - answers: yes to both
//
// Time Complexity: O(N) where N is the length of the strings.
//   - We iterate through the strings to create frequency maps. Map construction takes linear time relative to string length.
//   - Map comparison takes O(K) where K is the number of unique characters. K <= N.
//
// Space Complexity: O(K) where K is the size of the character set (number of unique characters).
//   - We store counts for each unique character.
//   - If the character set is fixed (e.g., ASCII), this is O(1).
//   - In the worst case (all characters unique), this is O(N).

fun String.checkPerm(t: String): Boolean {
    if (this.length != t.length) return false
    val thisCounts = this.groupingBy { it }.eachCount()
    val tCounts = t.groupingBy { it }.eachCount()
    return thisCounts == tCounts
}

fun test(name: String, result: Boolean) {
    println("Test '$name': ${if (result) "PASSED" else "FAILED"}")
}

// Tests
test("Empty strings", "".checkPerm(""))
test("Different lengths", !"a".checkPerm("aa"))
test("Valid permutation", "pedram".checkPerm("adepmr"))
test("Case sensitive", !"pEdram".checkPerm("adepmr"))
test("Different chars same length", !"pedram".checkPerm("pedaam"))
test("Trailing whitespace", !"pedram".checkPerm("pedram "))
