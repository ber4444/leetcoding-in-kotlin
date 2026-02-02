package BitManipulation

// return AND of all ints in a range
// tip: use shifting
fun solution(a: Int, b: Int): Int {
    var shiftCount = 0
    var x = a
    var y = b

    while (x != y && x > 0) {
        x = x shr 1
        y = y shr 1
        shiftCount++
    }
    return x shl shiftCount
}

println(Integer.toBinaryString(2))
println(Integer.toBinaryString(3))
println(Integer.toBinaryString(4))
println(solution(2,4) == 0)