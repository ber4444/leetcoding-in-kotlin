package linkedLists

class Node(var data: Int){
    var next: Node? = null
}

class LinkedList {
    var head: Node? = null

    fun add(newNode: Node) {
        val lastNode = last()
        if (lastNode != null) {
            lastNode.next = newNode
        } else {
            head = newNode
        }
    }

    private fun last(): Node? {
        var node = head
        while (node?.next != null) {
            node = node.next
        }
        return node
    }
}

// e.g. a->b->c->d->c [same c as before]
// detect if it has loop by using a fastRunner (moves 2 steps at a time) and a slowRunner (moves 1 step at a time) and see if they collide
// Time Complexity: O(N) - The fast pointer will catch up to the slow pointer within a bounded number of steps relative to the list length.
// Space Complexity: O(1) - Uses only two pointers (slow and fast) regardless of list size.
fun isCircular(node: Node): Boolean {
    var slow = node
    var fast = node
    while (fast.next != null) {
        slow = slow.next ?: return false
        fast = fast.next?.next ?: return false
        if (slow === fast) return true
    }
    return false
}

// Test Case 1: Simple Loop
println("--- Test Case 1: Simple Loop (self-loop) ---")
var ll = LinkedList()
ll.add(Node(1))
ll.add(Node(2))
// Create a loop: 1 -> 1 (node 2 becomes unreachable/lost in this specific assignment)
ll.head?.next = ll.head 
println("Is circular: ${isCircular(ll.head!!)}")

// Test Case 2: No Loop
println("\n--- Test Case 2: No Loop ---")
var ll2 = LinkedList()
ll2.add(Node(1))
ll2.add(Node(2))
ll2.add(Node(3))
println("Is circular: ${isCircular(ll2.head!!)}")

// Test Case 3: Larger Loop (1 -> 2 -> 3 -> 4 -> 2...)
println("\n--- Test Case 3: Larger Loop ---")
var ll3 = LinkedList()
val n1 = Node(1)
val n2 = Node(2)
val n3 = Node(3)
val n4 = Node(4)

ll3.head = n1
n1.next = n2
n2.next = n3
n3.next = n4
n4.next = n2 // Loop back to n2

println("Is circular: ${isCircular(ll3.head!!)}")