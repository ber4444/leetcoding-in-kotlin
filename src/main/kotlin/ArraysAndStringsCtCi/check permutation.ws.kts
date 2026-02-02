package ArraysAndStringsCtCi

// O(n) solution to check if str1 is permutation of str2
// clarify if the permutation comparison is case sensitive, and if white space counts - answers: yes to both
fun String.checkPerm(t: String): Boolean {
    if (this.length != t.length) return false
    val thisCounts = this.groupingBy { it }.eachCount()
    val tCounts = t.groupingBy { it }.eachCount()
    return thisCounts == tCounts
}

"".checkPerm("")
! "a".checkPerm("aa")
"pedram".checkPerm("adepmr")
! "pEdram".checkPerm("adepmr")
! "pedram".checkPerm("pedaam")
! "pedram".checkPerm("pedram ")