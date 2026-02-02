package DynamicProgramming

// find subarray with largest sum, return the sum
fun IntArray.maxSumSubarray(): Int {
    var maxEnding = this[0]
    var maxSoFar = this[0]

    for (i in 1 until size) {
        maxEnding = maxOf(this[i], maxEnding + this[i])
        maxSoFar = maxOf(maxSoFar, maxEnding)
    }

    return maxSoFar
}

val v = intArrayOf(5, 2, -1, 3, -4, -2)
println(v.maxSumSubarray() == 9)