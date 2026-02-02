package Math

fun Int.palindrome(): Boolean {
    if (this < 0) return false
    if (this % 10 == 0 && this != 0) return false

    var original = this
    var reversed = 0

    while (original > reversed) {
        reversed = reversed * 10 + original % 10
        original /= 10
    }

    return reversed == original || reversed / 10 == original
}

println(1001.palindrome())
println(123.palindrome())