package ArraysAndStrings

/**
 * Time Complexity: O(N) where N is the length of the string.
 * Space Complexity: O(N) for the result string.
 */
// compress a str (composed of a-z, A-Z chrs only) using the count of repeated chrs
fun String.compress(): String {
    if (!matches(Regex("[a-zA-Z]+"))) throw IllegalArgumentException()

    val result = buildString {
        var i = 0
        val len = this@compress.length
        while (i < len) {
            var count = 1
            while (i != len - 1 && this@compress[i + 1] == this@compress[i]) {
                i++
                count++
            }
            append(this@compress[i])
            append(count)
            i++
        }
    }

    return if (result.length > length) this else result
}

fun runTests() {
    val testCases = listOf(
        "aabccccccccdeFFFFggggggghi" to "a2b1c8d1e1F4g7h1i1",
        "aaaaaaaaaa" to "a10",
        "pedram" to "pedram"
    )

    println("Running tests...")
    var passed = 0
    for ((input, expected) in testCases) {
        val result = input.compress()
        if (result == expected) {
            println("PASS: $input -> $result")
            passed++
        } else {
            println("FAIL: $input -> $result (Expected: $expected)")
        }
    }

    try {
        "aa/bc".compress()
        println("FAIL: aa/bc -> No Exception (Expected: IllegalArgumentException)")
    } catch (e: IllegalArgumentException) {
        println("PASS: aa/bc -> Threw IllegalArgumentException")
        passed++
    } catch (e: Exception) {
        println("FAIL: aa/bc -> Threw ${e::class.simpleName} (Expected: IllegalArgumentException)")
    }
    
    println("Tests completed: $passed/${testCases.size + 1} passed.")
}

runTests()