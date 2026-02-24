package DynamicProgramming

// given different coin values (with infinite amount of each), and a given total,
// calculate how many coins we need to pay the total

// Time Complexity: O(total * N) where N is the number of coin denominations.
// We iterate from 1 to total, and for each amount, we iterate through all coins.
// Space Complexity: O(total) because we use an array of size total + 1 to store the minimum coins for each amount.
fun IntArray.coinChange(total: Int): Int {
    val MAX = total + 1
    val dp = IntArray(total + 1) { MAX }
    dp[0] = 0

    for (i in 1..total) {
        for (coin in this) {
            if (i >= coin && dp[i - coin] < MAX) {
                dp[i] = minOf(dp[i - coin] + 1, dp[i])
            }
        }
    }

    return if (dp[total] < MAX) dp[total] else -1
}

val coins = intArrayOf(25, 10, 5, 1)
println("Coins: ${coins.contentToString()}, Total: 44 -> ${coins.coinChange(44)} (Expected: 7)")
println(coins.coinChange(44) == 7)

// Additional Tests
val coins2 = intArrayOf(1, 2, 5)
println("Coins: ${coins2.contentToString()}, Total: 11 -> ${coins2.coinChange(11)} (Expected: 3)")
println(coins2.coinChange(11) == 3)

val coins3 = intArrayOf(2)
println("Coins: ${coins3.contentToString()}, Total: 3 -> ${coins3.coinChange(3)} (Expected: -1)")
println(coins3.coinChange(3) == -1)

val coins4 = intArrayOf(1)
println("Coins: ${coins4.contentToString()}, Total: 0 -> ${coins4.coinChange(0)} (Expected: 0)")
println(coins4.coinChange(0) == 0)
