package ArraysAndStringsCtCi

// assume that n is non-negative
fun IntArray.rotateBy(rotateBy: Int): IntArray {
    val v = reversedArray()
    val k = rotateBy % size
    return v.copyOfRange(0, k).reversedArray() + v.copyOfRange(k, v.size).reversedArray()
}

for (e in intArrayOf(1, 2, 3, 4, 5, 6, 7).rotateBy(3)) print("$e ")
for (e in intArrayOf(1, 2, 3, 4, 5, 6, 7).rotateBy(10)) print("$e ")
