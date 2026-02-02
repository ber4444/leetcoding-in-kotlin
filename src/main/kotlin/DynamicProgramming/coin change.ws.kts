package DynamicProgramming

// given different coin values (with infinite amount of each), and a given total,
// calculate how many coins we need to pay the total
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
println(coins.coinChange(44) == 7)