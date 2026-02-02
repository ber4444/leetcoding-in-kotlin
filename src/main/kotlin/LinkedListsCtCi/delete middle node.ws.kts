package LinkedListsCtCi

class Node<T>(var data: T) {
    var next: Node<T>? = null

    fun getList(): String = if (next != null) {
        "$data->${next?.getList()}"
    } else {
        data.toString()
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
            node = node.next
        }
        return node
    }
}

// given access to a single node only in the middle of a singly linked list (no access to head), delete it
fun Node<Int>?.deleteNode(): Boolean {
    if (this == null || next == null) return false

    data = next!!.data
    next = next!!.next
    return true
}

var ll = LinkedList<Int>()
for (i in 0..6) ll.add(i)

ll.head?.next?.next?.next?.next.deleteNode() // delete node 4

ll.head?.getList() == "0->1->2->3->5->6"
