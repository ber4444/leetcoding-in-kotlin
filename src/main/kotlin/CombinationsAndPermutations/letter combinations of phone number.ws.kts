package CombinationsAndPermutations

// return all letter combinations that a digit string could represent on a phone board
// Time Complexity: O(4^N * N), where N is the length of the string.
// In the worst case (e.g., digits 7 or 9), each digit maps to 4 letters.
// There are up to 4^N combinations, and forming each string takes O(N).
// Space Complexity: O(4^N * N) to store the result list.
fun String.combinations(): List<String> {
    val dict = arrayOf("0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ")

    return when {
        isEmpty() -> emptyList()
        length == 1 -> dict[first().digitToInt()].map { it.toString() }
        else -> {
            val firstCombinations = dict[first().digitToInt()].map { it.toString() }
            val restCombinations = substring(1).combinations()
            firstCombinations.flatMap { first -> restCombinations.map { rest -> first + rest } }
        }
    }
}

println("2".combinations())
println("23".combinations()) // AD AE AF BE ... CF
println("".combinations())
println("7".combinations())
println("234".combinations())