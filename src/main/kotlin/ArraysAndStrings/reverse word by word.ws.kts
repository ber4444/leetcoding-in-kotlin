package ArraysAndStringsCtCi

fun String.reverseWords() = split(" ").reversed().joinToString(" ")

"hello world!".reverseWords() == "world! hello"