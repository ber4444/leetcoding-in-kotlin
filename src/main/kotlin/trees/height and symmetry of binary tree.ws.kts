package trees

data class Node<T>(
    val value: T,
    var leftNode: Node<T>? = null,
    var rightNode: Node<T>? = null
) {
    fun link(left: Node<T>?, right: Node<T>?) = apply {
        leftNode = left
        rightNode = right
    }

    // Time Complexity: O(n) - traverses all nodes
    // Space Complexity: O(n) - recursion stack in worst case (skewed tree), O(log n) for balanced tree
    fun height(node: Node<T>? = this): Int =
        node?.let { 1 + maxOf(height(node.leftNode), height(node.rightNode)) } ?: -1

    // Time Complexity: O(n) - traverses all nodes
    // Space Complexity: O(n) - recursion stack in worst case
    fun isSymmetric(left: Node<T>? = leftNode, right: Node<T>? = rightNode): Boolean {
        if (left == null && right == null) return true
        return (left != null && right != null) &&
                left.value == right.value &&
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

println("Tree 1 (Structurally symmetric, but values differ):")
println("Height: ${a.height()}") // Expected: 2
println("Is Symmetric: ${a.isSymmetric()}") // Expected: false

/*
 *        [1]
 *       /   \
 *     [2]    [2]
 *     / \    / \
 *  [3]  [4] [4] [3]
 */
val root = Node(1)
val n2Left = Node(2)
val n2Right = Node(2)
val n3Left = Node(3)
val n4Left = Node(4)
val n4Right = Node(4)
val n3Right = Node(3)

root.link(n2Left, n2Right)
n2Left.link(n3Left, n4Left)
n2Right.link(n4Right, n3Right)

println("\nTree 2 (Fully symmetric):")
println("Height: ${root.height()}") // Expected: 2
println("Is Symmetric: ${root.isSymmetric()}") // Expected: true
