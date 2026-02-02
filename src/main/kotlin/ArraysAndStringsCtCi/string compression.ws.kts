package ArraysAndStringsCtCi

// compress a str (composed of a-z, A-Z chrs only) using the count of repeated chrs
fun String.compress(): String {
    if (!matches(Regex("[a-zA-Z]+"))) throw IllegalArgumentException()

    val result = buildString {
        var i = 0
        while (i < length) {
            var count = 1
            while (i != length - 1 && this@compress[i + 1] == this@compress[i]) {
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

"aabccccccccdeFFFFggggggghi".compress() == "a2b1c8d1e1F4g7h1i1"
"aaaaaaaaaa".compress() == "a10"
"pedram".compress() == "pedram"
println(try {
    "aa/bc".compress()
    false
} catch (e: Exception) {
    true
})