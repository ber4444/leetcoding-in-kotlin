package CombinationsAndPermutations

// combinations are different from permutations in that the order of elements matters for the latter only
// in this exercise, return all combinations of k numbers out of 1..n
//
// Time Complexity: O(C(n, k) * k)
// We generate C(n, k) combinations. For each valid combination, we take O(k) time to copy the list into the result.
//
// Space Complexity: O(k)
// We rely on recursion depth of k and the temporary list 'current' of size k.
// (This excludes the space required for the output result)
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

println("Test case 1: n=10, k=2")
println(combinations(10,2))

println("\nTest case 2: n=4, k=2")
// Expected: [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
println(combinations(4, 2))

println("\nTest case 3: n=1, k=1")
// Expected: [[1]]
println(combinations(1, 1))

println("\nTest case 4: n=5, k=0")
// Expected: [[]]
println(combinations(5, 0))

println("\nTest case 5: n=3, k=4")
// Expected: []
println(combinations(3, 4))