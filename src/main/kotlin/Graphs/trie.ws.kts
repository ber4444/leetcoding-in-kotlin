package Graphs

// Tries, or prefix trees, are n-ary trees used for storing the words for prefix lookups (kind of like a hashtable that can do partial matches)
// Can check if a string is a valid prefix in O(k) time, where k is the length of the string
class Trie {
    data class Node(var word: String? = null, val childNodes: MutableMap<Char, Node> = mutableMapOf())

    private val root = Node()

    fun insert(word: String) {
        var currentNode = root
        for (char in word) {
            currentNode = currentNode.childNodes.getOrPut(char) { Node() }
        }
        currentNode.word = word
    }

    fun startsWith(word: String): Boolean {
        var currentNode = root
        for (char in word) {
            val nextNode = currentNode.childNodes[char] ?: return false
            currentNode = nextNode
        }
        return currentNode.word == null
    }

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
            results.add(prefix)
        }
        node?.childNodes?.forEach { (char, childNode) ->
            results.addAll(collectMatches(prefix + char, childNode))
        }
        return results
    }
}

val trie = Trie()
listOf(
    "hello",
    "help",
    "helicopter",
    "hero",
    "hope",
    "echo",
    "hotel",
    "hot",
    "hop"
).forEach { trie.insert(it) }
println(trie.startsWith("he"))
println(trie.startsWith("xyz"))
println(trie.prefixMatch("he"))
