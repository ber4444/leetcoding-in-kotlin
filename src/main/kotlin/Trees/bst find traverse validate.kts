package Trees

// a BST is a special binary (2 children for each parent) tree where elements on the
// left are smaller than the parent, and the ones on the right are greater than the parent
// (node: Arrays.binarySearch is in the standard library - only applies to sorted arrays, run in O(log n))
// finding and insertion in a balanced bst is also O(log n)... note: AVL tree and RBT are self-balancing 
// in the kotlin library, we also have list.binarySearch(item)
/*
  +------ 5
  |       |
+ 3 +     9
|   |
1   4
we search is top-down and at each level check if the element we wanna find is bigger or smaller than the one we have there
in a balanced BST, both insert() and find() has O(log n) runtime - both of which are O(n) is an unbalanced BST
Traversals:
- inorder: left->root->right (explore left subtree, then root, then explore right subtree)
- preorder: root->left->right
- postorder: left->right->root
 */
class Node(
    // these arguments are applicable to any binary tree, not just for a bst
    var value: Int,
    var left: Node? = null,
    var right: Node? = null) {

    // note: while finding an element in an unsorted array is O(n), finding one is a BST is O(log n)
    // inserting into a sorted array is also O(n), while inserting into a BST is O(log n)
    // Time Complexity: O(h), where h is the height of the tree. In a balanced BST, h = log n. In the worst case (skewed tree), h = n.
    // Space Complexity: O(h) due to recursion stack.
    fun find(element: Int): Node? = when {
        this.value > element -> left?.find(element)
        this.value < element -> right?.find(element)
        else -> this
    }

    // non-recursive find
    // Time Complexity: O(h), where h is the height of the tree. In a balanced BST, h = log n. In the worst case, h = n.
    // Space Complexity: O(1) as it is iterative.
    fun find2(value: Int): Node? {
        var current: Node? = this
        while (current != null) {
            if (current.value == value) return current
            current = if (value < current.value) current.left else current.right
        }
        return null
    }

    // Time Complexity: O(h), where h is the height of the tree.
    // Space Complexity: O(h) due to recursion stack.
    fun insert(element: Int) {
        if (element > this.value)
            if (this.right == null) this.right = Node(element)
            else this.right?.insert(element)
        else if (element < this.value)
            if (this.left == null) this.left = Node(element)
            else this.left?.insert(element)
    }

    // visit the tree sequentially in order
    // this is applicable to any binary tree, not just for a bst
    // Time Complexity: O(n), where n is the number of nodes in the tree, as we visit each node once.
    // Space Complexity: O(h) due to recursion stack.
    fun traverseInOrder(visit:  (Int) -> Unit) {
        left?.traverseInOrder(visit) // left subtree
        visit(value) // root
        right?.traverseInOrder(visit) // right subtree
    }

    // pre-order traverse would be visit root, then recursively left, then recursively right
    // post-order would be left, then right, then root

    // Time Complexity: O(h), where h is the height of the tree (leftmost path).
    // Space Complexity: O(h) due to recursion stack.
    val min: Node?
        get() = left?.min ?: this

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false

        if (value != other.value) return false
        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }
}

// Time Complexity: O(n), as we visit each node once to validate the property.
// Space Complexity: O(h) due to recursion stack.
fun validate(tree: Node?): Boolean {
    // using Long to avoid overflow issues when node value is Int.MAX_VALUE or Int.MIN_VALUE
    return _validate(tree, Long.MIN_VALUE, Long.MAX_VALUE)
}
fun _validate(tree: Node?, min: Long, max: Long): Boolean {
    tree?.let { node ->
        if (node.value < min || node.value > max) return false
        return _validate(node.left, min, node.value.toLong()-1) && _validate(node.right, node.value.toLong()+1, max)
    } ?: return true
}

// Testing Implementation
println("--- BST Tests ---")
val tree = Node(60) // root - ideally the middle of the array if that is sorted so that the tree will end up being balanced
val v = arrayOf(30, 33, 40, 20, 30, 125, 70, 90, 6)
println("Inserting: ${v.joinToString(", ")}")
for (e in v) tree.insert(e)

println("Find 33 (exists): ${tree.find(33) != null}")
println("Find 34 (doesn't exist): ${tree.find(34) != null}")
println("Validate Tree (should be true): ${validate(tree)}")

print("In-order Traversal: ")
tree.traverseInOrder { print("$it ") }
println()

println("Min value: ${tree.min?.value}")

val tree2 = Node(60) // same root as above
for (e in v) tree2.insert(e)
// if values and topology match, it will return true, like in this case
println("Tree equality check: ${tree == tree2}")

// Test Invalid Tree
val invalidTree = Node(10)
invalidTree.left = Node(12) // Invalid: left child > parent
println("Validate Invalid Tree (should be false): ${validate(invalidTree)}")
