package Matrix

// print a matrix in spiral pattern
// see shorter solutions at https://leetcode.com/problems/spiral-matrix/solution/
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

val v = arrayOf(
    intArrayOf(0,1,2,3,4),
    intArrayOf(13,14,15,16,5),
    intArrayOf(12,19,18,17,6),
    intArrayOf(11,10,9,8,7))
println(v.spiral())
val weird = arrayOf(
    intArrayOf(1),
    intArrayOf(2),
    intArrayOf(3)
)
println(weird.spiral())
val weird2 = arrayOf(intArrayOf(4,5,6))
println(weird2.spiral())