package BitManipulation

// return number of 1 bits a number has (assume it's positive)
// tip: "and" returns 1 if two bits are both one
fun Int.numberOfOnes(): Int {
    var count = 0
    var x = this
    while (x > 0) {
        count++
        x = x and (x - 1)
    }
    return count
}

println(Integer.toBinaryString(11))
println(11.numberOfOnes() == 3)