package LinkedListsCtCi

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

// O(n) time, O(1) space solution for:
// find the Nth to last element of a singly linked list (size of list unknown)
// here k=1 is last element, k=2 is 2nd last, etc
// (if the size of the list is known, just iterate to the element at length-k position)
fun Node<Int>?.nthToLast(k: Int): Node<Int>? {
    var first = this    // will remain k nodes ahead
    var second = this   // will denote the element at position length-k

    // Move first k nodes into the list
    repeat(k) {
        if (first != null) {
            first = first.next
        } else {
            return null // Out of bounds
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

var ll = LinkedList<Int>()
for (i in 0..6) ll.add(i)

ll.head?.nthToLast(4)?.data == 3
