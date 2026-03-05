package matrix

// print a matrix in spiral pattern
// see shorter solutions at https://leetcode.com/problems/spiral-matrix/solution/

/**
 * Time Complexity: O(M * N) where M is the number of rows and N is the number of columns.
 * We visit each element exactly once.
 *
 * Space Complexity: O(M * N) because we use a `printed` boolean matrix to keep track of visited cells.
 * This can be optimized to O(1) (excluding output) by manipulating boundaries (top, bottom, left, right) instead of using a visited array.
 */
fun Array<IntArray>.spiral(): List<Int> {
    if (isEmpty() || this[0].isEmpty()) return listOf()
    if (size == 1) return flatMap { it.toList() }
    if (this[0].size == 1) return map { it[0] }

    // get number of elements
    val n = size * this[0].size
    val ret = ArrayList<Int>(n)
    val printed = Array(size) { BooleanArray(this[0].size) }
    val middleRow = if (size % 2 == 0) (size - 1) / 2 else size / 2
    val middleCol = if (this[0].size % 2 == 0) (this[0].size - 1) / 2 else this[0].size / 2
    var row = 0
    var col = 0
    var depth = 0

    while (row <= middleRow && col <= middleCol && depth <= middleRow && depth <= middleCol) {
        row = depth
        col = depth

        while (col < this[0].size - depth) {
            if (!printed[row][col]) {
                ret.add(this[row][col])
                printed[row][col] = true
            }
            col++
        }
        col--
        row++
        while (row < this.size - depth) {
            if (!printed[row][col]) {
                ret.add(this[row][col])
                printed[row][col] = true
            }
            row++
        }
        row--
        col--
        while (col >= depth) {
            if (!printed[row][col]) {
                ret.add(this[row][col])
                printed[row][col] = true
            }
            col--
        }
        col++
        row--
        while (row > depth) {
            if (!printed[row][col]) {
                ret.add(this[row][col])
                printed[row][col] = true
            }
            row--
        }
        depth++
    }
    return ret
}

fun check(caseName: String, actual: List<Int>, expected: List<Int>) {
    if (actual != expected) {
        println("FAIL: $caseName. Expected $expected but got $actual")
    } else {
        println("PASS: $caseName")
    }
}

val v = arrayOf(
    intArrayOf(0,1,2,3,4),
    intArrayOf(13,14,15,16,5),
    intArrayOf(12,19,18,17,6),
    intArrayOf(11,10,9,8,7))
check("v (4x5)", v.spiral(), (0..19).toList())

val weird = arrayOf(
    intArrayOf(1),
    intArrayOf(2),
    intArrayOf(3)
)
check("weird (3x1)", weird.spiral(), listOf(1, 2, 3))

val weird2 = arrayOf(intArrayOf(4,5,6))
check("weird2 (1x3)", weird2.spiral(), listOf(4, 5, 6))

val square = arrayOf(
    intArrayOf(1, 2),
    intArrayOf(4, 3)
)
check("square (2x2)", square.spiral(), listOf(1, 2, 3, 4))

val single = arrayOf(intArrayOf(42))
check("single (1x1)", single.spiral(), listOf(42))

val empty = arrayOf<IntArray>()
check("empty", empty.spiral(), listOf())
