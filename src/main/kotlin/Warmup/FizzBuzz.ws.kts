package Warmup

(1..100).forEach { i ->
    val s = buildString {
        if (i % 3 == 0) append("Fizz")
        if (i % 5 == 0) append("Buzz")
    }
    println(s.ifEmpty { i })
}