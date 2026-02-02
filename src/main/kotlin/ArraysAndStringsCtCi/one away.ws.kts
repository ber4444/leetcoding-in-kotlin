package ArraysAndStringsCtCi

import kotlin.math.abs

// O(n) time solution to check if the difference between 2 strings is 1 character (insert/remove/replace)
fun String.oneAway(str: String): Boolean {
    if (abs(length - str.length) > 1) return false

    val (shorterStr, longerStr) = if (length < str.length) this to str else str to this

    var foundDifference = false
    var idx1 = 0
    var idx2 = 0

    while (idx1 < shorterStr.length && idx2 < longerStr.length) {
        if (shorterStr[idx1] != longerStr[idx2]) {
            if (foundDifference) return false
            foundDifference = true

            if (shorterStr.length == longerStr.length) idx1++
        } else {
            idx1++
        }
        idx2++
    }
    return true
}

"pedram".oneAway("pedram")
"pedram".oneAway("edram")
"pedram".oneAway("peram")
"pedram".oneAway("pedra")
"pedram".oneAway("nedram")
"pedram".oneAway("pedpam")
"pedram".oneAway("pedran")
! "ppeedram".oneAway("pedram")
! "ppedram".oneAway("pedran")
! "pedram".oneAway("pedrann")