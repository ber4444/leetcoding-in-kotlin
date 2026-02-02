package Warmup

fun sum(n: Int): Int =
    if (n <= 0) 0 else n + sum(n - 1)

/*
call stack for the above:
sum(3) -> 2
 sum(2) -> 1
  sum(1)
   sum(0)
 and n=3 + 2 + 1 = 6, both time and space complexity being O(n) because of recursion
 */

fun fibonacci(n: Int): Int = when {
    n <= 0 -> 0
    n == 1 -> 1
    else -> fibonacci(n - 1) + fibonacci(n - 2)
}

/*
the time complexity of this is O(2^n) because the call tree has n depth and it's a binary tree
the space complexity is O(n) because only that many notes of the tree exist at any given time
TIME complexity can be improved to O(n) as well using memoization, see DP/fibonacci in this project
 */

/*
runtimes from fast to slow:
log n --> e.g. binary search in sorted array which divides N by 2 each time (e.g. log 16 is 4 steps... 2^4=16)
n
n log n
n^2
2^n
n!
n^n
...
 */

// factorial computation is actually O(n) time:
// 5! = 5*4*3*2*1
fun factorial(n: Int): Int = when {
    n < 0 -> -1
    n == 0 -> 1
    else -> n * factorial(n - 1)
}
