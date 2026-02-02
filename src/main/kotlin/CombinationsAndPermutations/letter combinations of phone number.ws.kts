package CombinationsAndPermutations

// return all letter combinations that a digit string could represent on a phone board
fun String.combinations(): List<String> {
    val dict = arrayOf("0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PRQS", "TUV", "WXYZ")

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