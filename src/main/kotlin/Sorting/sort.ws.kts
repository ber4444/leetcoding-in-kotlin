package Sorting

// (note: Arrays.sort is in the standard library, and Lists have a .sorted() extension - or can call sort() on a mutable list)
//  merge sort - O(n log n) for all cases (which is slower than O(n))
// down side is that merging the arrays needs O(n) space
// note: Collections.sort uses merge sort, while Arrays.sort uses quick sort (the latter is also O(n log n) but has worse case of O(n^2))
// using a Heap [tree] data structure is also O(n log n)

// start and end are the starting and ending indexes
fun IntArray.sort(start: Int, end: Int): IntArray {
    if (start < end) {
        val middle = (start + end) / 2
        sort(start, middle) // sort left
        sort(middle + 1, end) // sort right
        merge(start, middle, end) // merge them back together
    }
    return this
}

fun IntArray.merge(start: Int, middle: Int, end: Int) {
    val left = copyOfRange(start, middle + 1)
    val right = copyOfRange(middle + 1, end + 1)

    var leftIndex = 0
    var rightIndex = 0

    for (i in start..end) {
        when {
            leftIndex <= left.size - 1 && (rightIndex >= right.size || left[leftIndex] <= right[rightIndex]) -> {
                this[i] = left[leftIndex]
                leftIndex++
            }
            else -> {
                this[i] = right[rightIndex]
                rightIndex++
            }
        }
    }
}

val v = intArrayOf(13,14,15,16,5)
v.sort(0,v.size-1)
for (element in v) print("$element ")