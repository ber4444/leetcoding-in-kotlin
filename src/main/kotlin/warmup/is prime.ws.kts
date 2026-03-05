package warmup

import kotlin.math.sqrt

// O(sqrt n) runtime:
// e.g. 33 is not prime because 3*11=33
// 5 is prime because 1*5 is the only way to get to it
// sqrt of 16 is 4 (=4*4)
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (x in 2..sqrt(n.toDouble()).toInt()) {
        if (n % x == 0) return false
    }
    return true
}

println(isPrime(1))  // Should be false
println(isPrime(2))  // Should be true
println(isPrime(5))  // Should be true
println(isPrime(33)) // Should be false
