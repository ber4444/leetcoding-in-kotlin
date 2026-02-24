package linkedLists

// Reverse the list, then check if the first half is same as the original
// Time Complexity: O(n) because reversing the list takes linear time and comparing sublists also takes linear time relative to n/2.
// Space Complexity: O(n) because we are creating a new list of size n when calling reversed().

fun isPalindrome(list: List<Int>): Boolean {
    val reversed = list.reversed()
    return list.subList(0, list.size / 2) == reversed.subList(0, reversed.size / 2)
}

// Tests
val palindromeOdd = listOf(0, 1, 2, 1, 0)
val notPalindrome = listOf(1, 2, 3)
val palindromeEven = listOf(1, 2, 2, 1)

println("Is $palindromeOdd palindrome? ${isPalindrome(palindromeOdd)}")
println("Is $notPalindrome palindrome? ${isPalindrome(notPalindrome)}")
println("Is $palindromeEven palindrome? ${isPalindrome(palindromeEven)}")