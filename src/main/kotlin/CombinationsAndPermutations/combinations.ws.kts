package CombinationsAndPermutations

// combinations are different from permutations in that the order of elements matters for the latter only
// in this exercise, return all combinations of k numbers out of 1..n
fun combinations(n: Int, k: Int): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    backtrack(mutableListOf(), k, result, n, 1)
    return result
}

fun backtrack(current: MutableList<Int>, k: Int, result: MutableList<List<Int>>, n: Int, start: Int) {
    if (current.size == k) {
        result.add(current.toList())
        return
    }
    for (i in start..n) {
        current.add(i)
        backtrack(current, k, result, n, i + 1)
        current.removeAt(current.lastIndex)
    }
}

println(combinations(10,2))