package DynamicProgramming

// O(n) time complexity with tail recursion
tailrec fun fib(x: Int, a: Int = 0, b: Int = 1): Int =
    if (x == 0) a else fib(x - 1, b, a + b)

// Iterative approach without tail recursion
fun iterative(n: Int): Int {
    if (n < 2) return n
    var prev = 0
    var curr = 1
    repeat(n - 1) {
        val next = prev + curr
        prev = curr
        curr = next
    }
    return curr
}

// O(n) solution with memoization - stores already computed values (space complexity is O(n))
fun fib2(x: Int, memo: Array<Int?>): Int {
    return when {
        x == 0 -> 0
        x == 1 -> 1
        memo[x] != null -> memo[x]!!
        else -> {
            memo[x] = fib2(x - 1, memo) + fib2(x - 2, memo)
            memo[x]!!
        }
    }
}

val n = 6
println(fib2(n, Array(n+1) { null }))
println(fib(n))
println(iterative(n))