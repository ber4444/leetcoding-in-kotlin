package Math

fun Int.reversed(): Int {
    var reversed = 0L // a reversed Int may not fit into an Int
    var number = this

    while (number != 0) {
        val lastDigit = number % 10 // e.g. 15 % 10 will return 5
        number /= 10
        reversed = reversed * 10 + lastDigit

        if (reversed > Int.MAX_VALUE || reversed < Int.MIN_VALUE) return 0
    }

    return reversed.toInt()
}

println("1234".reversed().toInt() == 1234.reversed())
println(1231232999.reversed() == 0)