package arraysAndStrings

/**
 * Time Complexity: O(N) where N is the size of the array.
 *   - reversedArray() takes O(N)
 *   - copyOfRange and reversedArray on slices take O(N) combined.
 *   - array concatenation takes O(N)
 *
 * Space Complexity: O(N)
 *   - We create a new reversed array of size N.
 *   - We create temporary arrays for slices and the final result.
 *   - This implementation is not in-place.
 */
// assume that n is non-negative
fun IntArray.rotateBy(rotateBy: Int): IntArray {
    val v = reversedArray()
    val k = rotateBy % size
    return v.copyOfRange(0, k).reversedArray() + v.copyOfRange(k, v.size).reversedArray()
}

val arr = intArrayOf(1, 2, 3, 4, 5, 6, 7)
println("Original: ${arr.contentToString()}")

val rotatedBy3 = arr.rotateBy(3)
println("Rotate by 3: ${rotatedBy3.contentToString()}")
// Expected: [5, 6, 7, 1, 2, 3, 4]

val rotatedBy10 = arr.rotateBy(10)
println("Rotate by 10: ${rotatedBy10.contentToString()}")
// Expected: [5, 6, 7, 1, 2, 3, 4] (same as rotate by 3 because 10 % 7 = 3)
