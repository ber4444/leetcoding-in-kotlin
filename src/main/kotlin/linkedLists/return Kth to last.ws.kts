package linkedLists

class Node<T>(var data: T) {
    var next: Node<T>? = null
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
            node = node.next
        }
        return node
    }
}

/**
 * Finds the Kth to last element of a singly linked list.
 *
 * Time Complexity: O(N) - where N is the number of nodes in the linked list.
 * Space Complexity: O(1) - we use only two pointers regardless of the list size.
 *
 * @param k The position from the end (1-based index).
 *          k=1 returns the last element, k=2 returns the second to last, etc.
 * @return The Kth to last node, or null if k is out of bounds (k > size or k <= 0).
 */
fun Node<Int>?.nthToLast(k: Int): Node<Int>? {
    if (k <= 0) return null
    var first = this    // will move k nodes ahead
    var second = this   // will denote the element at position length-k

    // Move first k nodes into the list
    repeat(k) {
        if (first != null) {
            first = first.next
        } else {
            return null // Out of bounds (k > length)
        }
    }

    // Move them at the same pace. When first hits the end,
    // second will be at the right element.
    while (first != null) {
        first = first.next
        second = second?.next
    }

    return second
}

// Tests
val ll = LinkedList<Int>()
for (i in 0..6) ll.add(i) // List: 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6

println("List: 0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6")

val k1 = 1
val result1 = ll.head.nthToLast(k1)?.data
println("k=$k1 (Last element): Expected 6, Got $result1. Pass: ${result1 == 6}")

val k4 = 4
val result4 = ll.head.nthToLast(k4)?.data
println("k=$k4: Expected 3, Got $result4. Pass: ${result4 == 3}")

val k7 = 7
val result7 = ll.head.nthToLast(k7)?.data
println("k=$k7 (First element): Expected 0, Got $result7. Pass: ${result7 == 0}")

val k8 = 8
val result8 = ll.head.nthToLast(k8)
println("k=$k8 (Out of bounds): Expected null, Got $result8. Pass: ${result8 == null}")

val k0 = 0
val result0 = ll.head.nthToLast(k0)
println("k=$k0 (Invalid k): Expected null, Got $result0. Pass: ${result0 == null}")
