package combinationsAndPermutations

// Backtracking problems can be thought of as trees where decisions are made at each node.
// When we evaluate a new node and decide it's not for us, we backtrack to the parent and
// investigate the next node. In doing so, we are pruning the recursion tree - rejecting invalid decisions.
/**
 * Prints all permutations of a string.
 *
 * Time Complexity: O(N * N!)
 * - There are N! permutations.
 * - Converting the character list to a string takes O(N).
 *
 * Space Complexity: O(N) auxiliary space
 * - O(N) for the recursion stack.
 * - (Excluding the space required to hold the output, which is O(N * N!)).
 */
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

println("--- Test 1: '123' ---")
println("Input: 123")
val result1 = "123".toCharArray().toMutableList().permutations()
println("Output: $result1")
println("Count: ${result1.size}") // Expected: 6

println("\n--- Test 2: 'abc' ---")
println("Input: abc")
val result2 = "abc".toCharArray().toMutableList().permutations()
println("Output: $result2")
println("Count: ${result2.size}") // Expected: 6

println("\n--- Test 3: 'a' ---")
println("Input: a")
val result3 = "a".toCharArray().toMutableList().permutations()
println("Output: $result3")
println("Count: ${result3.size}") // Expected: 1

println("\n--- Test 4: '' (Empty) ---")
println("Input: (empty)")
val result4 = "".toCharArray().toMutableList().permutations()
println("Output: $result4")
println("Count: ${result4.size}") // Expected: 1 (containing empty string)
