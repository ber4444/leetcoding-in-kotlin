package trees

import java.util.ArrayDeque

// a tree is an undirected graph in which any two nodes are connected by exactly one path
// we have access to the root node exclusively and other nodes can be accessed via that
class TreeNode<T>(val value: T) {
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    // Time Complexity: O(1)
    // Space Complexity: O(1)
    fun add(child: TreeNode<T>) = children.add(child)

    // Time Complexity: O(N) where N is the number of nodes
    // Space Complexity: O(H) where H is the height of the tree (recursion stack)
    fun dfs(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        children.forEach { it.dfs(visit) }
    }

    // Time Complexity: O(N) where N is the number of nodes
    // Space Complexity: O(W) where W is the maximum width of the tree (queue size)
    fun bfs(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        val queue = ArrayDeque<TreeNode<T>>()
        children.forEach { queue.add(it) }
        var node = queue.poll()
        while (node != null) {
            visit(node)
            node.children.forEach { queue.add(it) }
            node = queue.poll()
        }
    }

    // modified bfs to print items on the same level of the tree in the same line
    // Time Complexity: O(N)
    // Space Complexity: O(W)
    fun printFormatted(visit: (TreeNode<T>) -> Unit) {
        val queue = ArrayDeque<TreeNode<T>>()
        var nodesLeftInCurrentLevel = 0
        queue.add(this)

        while (queue.isNotEmpty()) {
            nodesLeftInCurrentLevel = queue.size // queue size is the number of nodes at the current level
            while (nodesLeftInCurrentLevel > 0) {
                val node = queue.poll()
                if (node != null) {
                    visit(node)
                    node.children.forEach { queue.add(it) }
                    nodesLeftInCurrentLevel--
                } else {
                    break
                }
            }
            println()
        }
    }
}

println("--- Manual Construction Test ---")
val hot = TreeNode("Hot")
hot.add(TreeNode("tea"))
hot.add(TreeNode("coffee"))
val cold = TreeNode("Cold")
val genericTree = TreeNode("Beverages").apply {
    add(hot)
    add(cold)
}
print("DFS: ")
genericTree.dfs { print("${it.value} ") }
println()
println("--- Formatted Print ---")
// print all nodes
genericTree.printFormatted { print("${it.value} ") }
println("--- Search Test ---")
// search for particular node
var found = false
genericTree.bfs { if (it.value=="tea") found = true }
println(if (found) "Found tea" else "Tea not found")

println("--- List Construction Test ---")
// read list into a tree:
val inputPairs = listOf(
    8 to 7,
    7 to 6,
    6 to 4,
    5 to 4,
    4 to 3,
    3 to 2,
    2 to 1,
    1 to null,
    9 to null
)
val tree = TreeNode(0) // using Int's for this sample but type is generic
for (i in inputPairs.size-1 downTo 0) {
    val item = inputPairs[i]
    if (item.second == null) tree.add(TreeNode(item.first))
    else tree.dfs {
        if (it.value == item.second) {
            // println("will add ${item.first} to parent ${it.value}")
            it.add(TreeNode(item.first))
        }
    }
}

println("Constructed Tree Structure:")
tree.printFormatted { print("${it.value} ") }