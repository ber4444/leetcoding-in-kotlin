package DynamicProgramming

import java.math.BigInteger
import kotlin.time.measureTimedValue

// Time Complexity: O(n), it's 5x faster than fib2() on my machine
// Space Complexity: O(1)
tailrec fun fib(n: Int, a: BigInteger = BigInteger.ZERO, b: BigInteger = BigInteger.ONE): BigInteger =
    if (n == 0) a else fib(n - 1, b, a + b)

// Time Complexity: O(n)
// Space Complexity: O(n) - due to recursion stack and memoization cache
val cache = mutableMapOf<Int, BigInteger>()
fun fib2(n: Int): BigInteger = cache.getOrPut(n) {
    if (n <= 1) BigInteger.valueOf(n.toLong()) else fib2(n - 1) + fib2(n - 2)
}

val n = 5000
val (ret2, time2) = measureTimedValue { fib2(n) }
println("Value with memoization (first 20 chars): ${ret2.toString().take(20)}...")
println("Time with memoization: ${time2.inWholeMilliseconds} ms")

val (ret, time) = measureTimedValue { fib(n) }
println("Value with tail recursion (first 20 chars): ${ret.toString().take(20)}...")
println("Time with tail recursion: ${time.inWholeMilliseconds} ms")
