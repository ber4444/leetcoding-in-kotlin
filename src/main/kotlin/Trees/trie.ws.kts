package Trees

// Tries, or prefix trees, are n-ary trees used for storing the words for prefix lookups (kind of like a hashtable that can do partial matches)
//
// Time Complexity:
//   Insert: O(L) where L is the length of the word
//   Search: O(L) where L is the length of the word
//   StartsWith: O(L) where L is the length of the prefix
//
// Space Complexity:
//   O(N * L) where N is the number of words and L is the average length of the word (in the worst case of no common prefixes)

class Trie {
    data class Node(var word: String? = null, val childNodes: MutableMap<Char, Node> = mutableMapOf())

    private val root = Node()

    // Inserts a word into the trie
    fun insert(word: String) {
        var currentNode = root
        for (char in word) {
            currentNode = currentNode.childNodes.getOrPut(char) { Node() }
        }
        currentNode.word = word
    }

    // Checks if the word is in the trie
    fun search(word: String): Boolean {
        var currentNode = root
        for (char in word) {
            val nextNode = currentNode.childNodes[char] ?: return false
            currentNode = nextNode
        }
        return currentNode.word != null
    }

    // Checks if there is any word in the trie that starts with the given prefix
    fun startsWith(prefix: String): Boolean {
        var currentNode = root
        for (char in prefix) {
            val nextNode = currentNode.childNodes[char] ?: return false
            currentNode = nextNode
        }
        return true
    }

    // Returns all words in the trie that start with the given prefix
    fun prefixMatch(prefix: String): List<String> {
        var current = root
        for (char in prefix) {
            val child = current.childNodes[char] ?: return emptyList()
            current = child
        }
        return collectMatches(prefix, current)
    }

    private fun collectMatches(prefix: String, node: Node?): List<String> {
        val results = mutableListOf<String>()
        if (node?.word != null) {
            results.add(node.word!!)
        }
        node?.childNodes?.forEach { (char, childNode) ->
            results.addAll(collectMatches(prefix + char, childNode))
        }
        return results
    }
}

val trie = Trie()
val words = listOf(
    "hello",
    "help",
    "helicopter",
    "hero",
    "hope",
    "echo",
    "hotel",
    "hot",
    "hop"
)
println("Inserting words: $words")
words.forEach { trie.insert(it) }

println("\n--- Testing startsWith ---")
println("startsWith('he'): ${trie.startsWith("he")} (Expected: true)")
println("startsWith('hot'): ${trie.startsWith("hot")} (Expected: true)")
println("startsWith('xyz'): ${trie.startsWith("xyz")} (Expected: false)")

println("\n--- Testing search (exact match) ---")
println("search('hello'): ${trie.search("hello")} (Expected: true)")
println("search('hel'): ${trie.search("hel")} (Expected: false)")
println("search('hope'): ${trie.search("hope")} (Expected: true)")

println("\n--- Testing prefixMatch ---")
println("prefixMatch('he'): ${trie.prefixMatch("he")}")
println("prefixMatch('ho'): ${trie.prefixMatch("ho")}")
println("prefixMatch('ec'): ${trie.prefixMatch("ec")}")
println("prefixMatch('z'): ${trie.prefixMatch("z")}")
