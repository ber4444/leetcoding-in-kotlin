package ArraysAndStringsCtCi

// O(N) solution for: check if a is substring b with rotation
fun String.isRotation(s: String): Boolean =
	length == s.length && isNotEmpty() && (this + this).indexOf(s) >= 0

"apple".isRotation("pleap") // ap[pleap]ple
"waterbottle".isRotation("erbottlewat")
! "camera".isRotation("macera")
