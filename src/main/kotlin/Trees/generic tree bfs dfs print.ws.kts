package Trees

import java.util.ArrayDeque

class TreeNode<T>(val value: T) {
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    fun add(child: TreeNode<T>) = children.add(child)

    fun dfs(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        children.forEach { it.dfs(visit) }
    }

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
    fun printFormatted(visit: (TreeNode<T>) -> Unit) {
        val queue = ArrayDeque<TreeNode<T>>()
        var nodesLeftInCurrentLevel = 0
        queue.add(this)

        while (queue.isNotEmpty()) {
            nodesLeftInCurrentLevel = queue.size // queue size is the number of nodes at the current level
            while (nodesLeftInCurrentLevel > 0) {
                val node = queue.poll()
                if (node != null) {
                    print("${node.value} ")
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

val hot = TreeNode("Hot")
hot.add(TreeNode("tea"))
hot.add(TreeNode("coffee"))
val cold = TreeNode("Cold")
val tree = TreeNode("Beverages").apply {
    add(hot)
    add(cold)
}
tree.dfs { println(it.value) }
println("---")
// print all nodes
tree.printFormatted { println(it.value) }
// search for particular node
tree.bfs { if (it.value=="tea") println("found") }

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
    val tree = Node(0) // using Int's for this sample but type is generic
    for (i in inputPairs.size-1 downTo 0) {
        val item = inputPairs.get(i)
        if (item.second == null) tree.add(Node(item.first)) 
        else tree.dfs { 
            if (it.value == item.second) {
                println("will add ${item.first} to parent ${it.value}")
                it.add(Node(item.first))
            } 
         }  
    }
