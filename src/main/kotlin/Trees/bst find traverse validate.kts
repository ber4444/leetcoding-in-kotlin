package Trees

// a BST is a special binary tree where elements on the left are smaller than ones on the right
// (node: Arrays.binarySearch is in the standard library - only applies to sorted arrays, and is faster
// than linear search - O(log n))
// finding and insertion in a balanced bst is also O(log n)... note: AVL tree is self-balancing BST
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
    fun find(element: Int): Node? = when {
        this.value > element -> left?.find(element)
        this.value < element -> right?.find(element)
        else -> this
    }

    // non-recursive find
    fun find2(value: Int): Node? {
        var current: Node? = this
        while (current != null) {
            if (current.value == value) return current
            current = if (value < current.value) current.left else current.right
        }
        return null
    }

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
    fun traverseInOrder(visit:  (Int) -> Unit) {
        left?.traverseInOrder(visit) // left subtree
        visit(value) // root
        right?.traverseInOrder(visit) // right subtree
    }

    // pre-order traverse would be visit root, then recursively left, then recursively right
    // post-order would be left, then right, then root


    val min: Node?
        get() = left?.min ?: this

    override fun equals(other: Any?): Boolean {
        return if (other != null && other is Node) {
            this.value == other.value &&
                    this.left == other.left &&
                    this.right == other.right
        } else false
    }
}
// O(1) time, O(log n) space
fun validate(tree: Node?): Boolean {
    return _validate(tree, Int.MIN_VALUE, Int.MAX_VALUE)
}
fun _validate(tree: Node?, min: Int, max: Int): Boolean {
    tree?.let { node ->
        if (node.value < min || node.value > max) return false
        return _validate(node.left, min, node.value-1) && _validate(node.right, node.value+1, max)
    } ?: return true
}
val tree = Node(60) // root - ideally the middle of the array if that is sorted so that the tree will end up being balanced
val v = arrayOf(30, 33, 40, 20, 30, 125, 70, 90, 6)
for (e in v) tree.insert(e)
println(tree.find(33) != null)
println(tree.find(34) != null)
println(validate(tree))
tree.traverseInOrder { print("$it ") }
println()
println(tree.min?.value)
val tree2 = Node(60)
for (e in v) tree2.insert(e)
// node: if it's not an Int tree, need to declare the type as Node<T: Comparable<T>>
println(tree.equals(tree2))