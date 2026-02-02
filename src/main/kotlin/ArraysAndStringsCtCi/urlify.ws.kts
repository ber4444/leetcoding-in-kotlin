package ArraysAndStringsCtCi

// O(n) time O(1) space solution to replace " " with "%20" in place
// trueLength means the number of chrs from the input that we want to consider
// use a CharArray because String is immutable so this way we can do it in one pass
// I guess StringBuilder would work too
fun CharArray.urlify(trueLength: Int): String {
    if (size == trueLength) return String(this)

    // triple the spaces to leave room for "%20"
    var targetIdx = trueLength + take(trueLength).count { it == ' ' } * 2 - 1
    // now edit the string backwards
    for (idx in trueLength - 1 downTo 0) {
        if (this[idx] != ' ') {
            this[targetIdx--] = this[idx]
        } else {
            this[targetIdx] = '0'
            this[targetIdx - 1] = '2'
            this[targetIdx - 2] = '%'
            targetIdx -= 3
        }
    }

    return String(this)
}

"Ped%20ram" == "Ped ram  ".toCharArray().urlify(7)
"%20Pedram" == " Pedram  ".toCharArray().urlify(7)
"Pedram%20%20" == "Pedram      ".toCharArray().urlify(8)
"Pedram" == "Pedram".toCharArray().urlify(6)
"Ped%20ram     " == "Ped ram       ".toCharArray().urlify(7)
"P%20e%20d%20r%20a%20m" == "P e d r a m          ".toCharArray().urlify(11)