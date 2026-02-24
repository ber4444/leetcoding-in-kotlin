package ArraysAndStrings

/**
 * Replaces all spaces in a string with '%20'.
 * Assumes the string has sufficient free space at the end to hold the additional characters,
 * and that we are given the "true" length of the string.
 *
 * Time Complexity: O(n) where n is the true length of the string.
 * Space Complexity: O(1) as we modify the array in place.
 */
fun CharArray.urlify(trueLength: Int): String {
    // Count spaces in the first trueLength characters
    var spaceCount = 0
    for (i in 0 until trueLength) {
        if (this[i] == ' ') {
            spaceCount++
        }
    }

    // If no spaces, return early
    if (spaceCount == 0) return String(this)

    // Calculate the new length required
    var targetIdx = trueLength + spaceCount * 2 - 1

    // Iterate backwards
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

// Tests
check("Ped%20ram" == "Ped ram  ".toCharArray().urlify(7)) { "Test 1 failed" }
check("%20Pedram" == " Pedram  ".toCharArray().urlify(7)) { "Test 2 failed" }
check("Pedram%20%20" == "Pedram      ".toCharArray().urlify(8)) { "Test 3 failed" }
check("Pedram" == "Pedram".toCharArray().urlify(6)) { "Test 4 failed" }
check("Ped%20ram     " == "Ped ram       ".toCharArray().urlify(7)) { "Test 5 failed" }
check("P%20e%20d%20r%20a%20m" == "P e d r a m          ".toCharArray().urlify(11)) { "Test 6 failed" }

"All tests passed"