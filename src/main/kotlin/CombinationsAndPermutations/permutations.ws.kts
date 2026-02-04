package CombinationsAndPermutations

// print all permutations of a string - O(n!) = n x (n-1) x ... x 1
// tip: use backtracking
fun MutableList<Char>.permutations(): List<String> {
    val result = mutableListOf<String>()
    backtrack(0, result)
    return result
}

fun MutableList<Char>.backtrack(start: Int, perms: MutableList<String>) {
    if (start == size) {
        perms.add(this.toCharArray().concatToString())
    } else {
        for (i in start until size) {
            swap(start, i)
            backtrack(start + 1, perms)
            swap(start, i)
        }
    }
}

fun MutableList<Char>.swap(start: Int, next: Int) {
    val tmp = this[start]
    this[start] = this[next]
    this[next] = tmp
}

println("123".toCharArray().toMutableList().permutations())