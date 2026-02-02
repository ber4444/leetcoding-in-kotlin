package Trees

data class Node<T>(
    val value: T,
    var leftNode: Node<T>? = null,
    var rightNode: Node<T>? = null
) {
    fun link(left: Node<T>?, right: Node<T>?) = apply {
        leftNode = left
        rightNode = right
    }

    // O(n) time - traverses all nodes, and O(n) space because of recursion
    fun height(node: Node<T>? = this): Int =
        node?.let { 1 + maxOf(height(node.leftNode), height(node.rightNode)) } ?: -1

    fun isSymmetric(left: Node<T>? = leftNode, right: Node<T>? = rightNode): Boolean {
        if (left == null && right == null) return true
        return (left != null && right != null) &&
                isSymmetric(left.leftNode, right.rightNode) &&
                isSymmetric(left.rightNode, right.leftNode)
    }
}

/*
 *        [A]
 *       /   \
 *     [B]    [C]
 *     / \    /  \
 *  [D]  [E] [F] [G]
 */
val a = Node('a')
val b = Node('b')
val c = Node('c')
val d = Node('d')
val e = Node('e')
val f = Node('f')
val g = Node('g')
a.link(b, c)
b.link(d, e)
c.link(f, g)
println(a.height().toString())
println(a.isSymmetric())