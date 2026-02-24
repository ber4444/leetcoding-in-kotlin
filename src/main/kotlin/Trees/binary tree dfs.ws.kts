package Trees

import java.util.ArrayDeque

data class Node<T>(
    val value: T,
    var leftNode: Node<T>? = null,
    var rightNode: Node<T>? = null
) {
    fun link(left: Node<T>?, right: Node<T>?) = apply {
        linkLeft(left)
        linkRight(right)
    }

    private fun linkLeft(left: Node<T>?) = apply { leftNode = left }
    private fun linkRight(right: Node<T>?) = apply { rightNode = right }
}

/**
 * Depth First Search (DFS) implementation for a binary tree using an iterative approach (Pre-order).
 *
 * Time Complexity: O(N) where N is the number of nodes in the tree, as each node is visited exactly once.
 * Space Complexity: O(H) where H is the height of the tree.
 *                   - In the worst case (skewed tree), the stack can grow to O(N).
 *                   - In the best case (balanced tree), the stack size is O(log N).
 *                   - The output list takes O(N) space.
 */
fun <T> dfs(root: Node<T>): List<Node<T>> {
    val stack = ArrayDeque<Node<T>>()
    val traversalList = mutableListOf<Node<T>>()

    stack.push(root)

    while (stack.isNotEmpty()) {
        val currentNode = stack.pop()
        
        // Push right child first so that left child is processed first (LIFO)
        currentNode.rightNode?.let { stack.push(it) }
        currentNode.leftNode?.let { stack.push(it) }
        
        // Add to traversal list.
        traversalList.add(currentNode)
    }
    return traversalList
}

/*
 *        [A]
 *       /   \
 *     [B]    [C]
 *     / \    /  \
 *  [D]  [E] [F] [G]
 *               / \
 *             [H] [I]
 */
val a = Node('a')
val b = Node('b')
val c = Node('c')
val d = Node('d')
val e = Node('e')
val f = Node('f')
val g = Node('g')
val h = Node('h')
val i = Node('i')

a.link(b, c)
b.link(d, e)
c.link(f, g)
g.link(h, i)

val result = dfs(a).map { it.value }
println("DFS Traversal: $result")

// Test the implementation
val expected = listOf('a', 'b', 'd', 'e', 'c', 'f', 'g', 'h', 'i')
check(result == expected) {
    "Test Failed! Expected: $expected, but got: $result"
}
println("Test Passed!")