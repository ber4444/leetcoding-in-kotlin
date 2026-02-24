package linkedLists

// given access to a single node only in the middle of a singly linked list (no access to head), delete it

class Node<T>(var data: T) {
    var next: Node<T>? = null

    fun getList(): String = if (next != null) {
        "$data->${next?.getList()}"
    } else {
        data.toString()
    }

    override fun toString(): String {
        return data.toString()
    }
}

class LinkedList<T> {
    var head: Node<T>? = null

    fun add(value: T) {
        val newNode = Node(value)
        val lastNode = last()
        if (lastNode != null) {
            lastNode.next = newNode
        } else {
            head = newNode
        }
    }

    private fun last(): Node<T>? {
        var node = head
        while (node?.next != null) {
            node = node?.next
        }
        return node
    }
}

/**
 * Deletes the current node from the linked list by copying the data from the next node
 * and skipping the next node.
 *
 * Time Complexity: O(1)
 * Space Complexity: O(1)
 */
fun Node<Int>?.deleteNode(): Boolean {
    if (this == null || next == null) return false

    data = next!!.data
    next = next!!.next
    return true
}

/**
 * Removes the second half of the linked list.
 *
 * Time Complexity: O(N) - where N is the number of nodes in the list.
 * Space Complexity: O(1)
 */
fun LinkedList<Int>.removeBackHalf() {
    var slow: Node<Int>? = head
    var fast: Node<Int>? = head
    var previous: Node<Int>? = null

    while (fast?.next != null) {
        previous = slow
        slow = slow?.next
        fast = fast?.next?.next
    }

    if (previous != null) {
        previous.next = null
    } else {
        head = null
    }
}

// Tests
println("--- Test 1: Delete middle node ---")
var ll = LinkedList<Int>()
for (i in 0..6) ll.add(i)
println("Original: ${ll.head?.getList()}")

// Delete node with value 4 (which is at index 4)
// 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6
// nodeToDelete should be the node with data 4
val nodeToDelete = ll.head?.next?.next?.next?.next
println("Deleting node with value: ${nodeToDelete?.data}")
val deleted = nodeToDelete.deleteNode() 
println("Delete success: $deleted")
println("Result: ${ll.head?.getList()}")
println("Correct: ${ll.head?.getList() == "0->1->2->3->5->6"}")

println("\n--- Test 2: Remove back half (Odd remaining length) ---")
// Current list: 0->1->2->3->5->6 (Length 6)
ll.removeBackHalf()
println("After removeBackHalf: ${ll.head?.getList()}")
// Length was 6. Half is 3. Should keep first 3: 0->1->2.
println("Correct: ${ll.head?.getList() == "0->1->2"}")

println("\n--- Test 3: Remove back half (Even length) ---")
var ll2 = LinkedList<Int>()
for (i in 1..4) ll2.add(i)
println("Original: ${ll2.head?.getList()}")
ll2.removeBackHalf()
println("After removeBackHalf: ${ll2.head?.getList()}")
println("Correct: ${ll2.head?.getList() == "1->2"}")

println("\n--- Test 4: Delete last node (Edge case) ---")
var ll3 = LinkedList<Int>()
ll3.add(10)
ll3.add(20)
val lastNode = ll3.head?.next
println("Attempting to delete last node (20)")
val deletedLast = lastNode.deleteNode()
println("Delete success: $deletedLast") // Should be false
println("List: ${ll3.head?.getList()}")
