package trees

// write an in-place algo to find the k-th smallest item in a BST
// (we could do an in-order traversal if we didn't have the in-place constraint)

class Node(var value: Int, var left: Node? = null, var right: Node? = null)

/**
 * Calculates the number of nodes in the subtree.
 * Time Complexity: O(N) where N is the number of nodes in the subtree.
 * Space Complexity: O(H) where H is the height of the tree (recursion stack).
 */
fun countNodes(n: Node?): Int {
    if (n == null) return 0
    return 1 + countNodes(n.left) + countNodes(n.right)
}

/**
 * Finds the k-th smallest element in the BST.
 *
 * Time Complexity:
 *  - Average Case (Balanced Tree): O(N).
 *    In each step, we traverse a height of the tree and calculate countNodes on a child.
 *    T(N) = T(N/2) + O(N) => O(N).
 *  - Worst Case (Skewed Tree): O(N^2).
 *    T(N) = T(N-1) + O(N) => O(N^2).
 *
 * Space Complexity:
 *  - O(H) for the recursion stack of countNodes.
 *  - kthSmallest itself is tail-recursive, so it uses O(1) stack space.
 *  - Total Space: O(H).
 */
tailrec fun kthSmallest(root: Node, k: Int): Int {
    val leftCount = countNodes(root.left)
    
    if (leftCount == k - 1) return root.value
    
    if (leftCount >= k) return kthSmallest(root.left!!, k)
    
    return kthSmallest(root.right!!, k - leftCount - 1)
}

// Test
val root = Node(5, Node(3, Node(2), Node(4)), Node(6))
// Tree:
//      5
//     / \
//    3   6
//   / \
//  2   4

println("Tree Structure:")
println("     5")
println("    / \\")
println("   3   6")
println("  / \\")
println(" 2   4")
println("---------")

val k1 = kthSmallest(root, 1)
println("k=1: Expected 2, Got $k1") 
check(k1 == 2) { "Test failed for k=1" }

val k2 = kthSmallest(root, 2)
println("k=2: Expected 3, Got $k2")
check(k2 == 3) { "Test failed for k=2" }

val k3 = kthSmallest(root, 3)
println("k=3: Expected 4, Got $k3")
check(k3 == 4) { "Test failed for k=3" }

val k4 = kthSmallest(root, 4)
println("k=4: Expected 5, Got $k4")
check(k4 == 5) { "Test failed for k=4" }

val k5 = kthSmallest(root, 5)
println("k=5: Expected 6, Got $k5")
check(k5 == 6) { "Test failed for k=5" }
