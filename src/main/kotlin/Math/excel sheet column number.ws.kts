package Math

// A B ... AA AB ...
// given these column titles in Excel, return the column number
// e.g. A -> 1, AA -> 27
fun String.colNum(): Int =
    fold(0) { result, char -> result * 26 + (char - 'A') + 1 }

println("AA".colNum())