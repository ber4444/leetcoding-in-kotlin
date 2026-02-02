package ArraysAndStringsCtCi

// assume the array is unsorted
fun IntArray.findKthLargest(k: Int): Int {
    sort()
    return this[size - k]
}

intArrayOf(2, 3, 1, 4, 5).findKthLargest(2)
