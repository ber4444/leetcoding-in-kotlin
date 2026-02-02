package Warmup

// O(sqrt n) runtime:
// e.g. 33 is not prime because 3*11=33
// 5 is prime because 1*5 is the only way to get to it
// sqrt of 16 is 4 (=4*4)
fun isPrime(n: Int): Boolean {
    for (x in 2 until kotlin.math.sqrt(n.toDouble()).toInt() + 1) {
        if (n % x == 0) return false
    }
    return true
}
isPrime(5)
isPrime(33)