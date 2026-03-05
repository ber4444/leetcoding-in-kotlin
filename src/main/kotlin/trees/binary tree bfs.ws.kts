package trees

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
 * Traverses the tree level by level.
 *
 * Time Complexity: O(N) where N is the number of nodes.
 * We visit each node exactly once.
 *
 * Space Complexity: O(N)
 * The queue stores nodes at the current level. In the worst case (a full binary tree),
 * the width is (N+1)/2, so space is O(N).
 * The result list also takes O(N) space.
 */
fun <T> bfs(root: Node<T>): List<Node<T>> {
    val queue = ArrayDeque<Node<T>>()
    val traversalList = mutableListOf<Node<T>>()

    queue.add(root)

    while (queue.isNotEmpty()) {
        val currentNode = queue.poll()
        currentNode.leftNode?.let { queue.add(it) }
        currentNode.rightNode?.let { queue.add(it) }
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

val result = bfs(a).map { it.value }
println("Result: $result")

// Test the implementation
val expected = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i')
if (result == expected) {
    println("Test Passed!")
} else {
    println("Test Failed. Expected: $expected, but got: $result")
}