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

// partition a linked list so that all nodes < x come before nodes >= x; no need to sort partitions
fun Node<Int>?.partition(x: Int): Node<Int>? {
	var node = this
	var head = node
	var tail = node

	while (node != null) {
		val next = node.next
		if (node.data < x) {
			// Insert node at head
			node.next = head
			head = node
		} else {
			// Insert node at tail
			tail?.next = node
			tail = node
		}
		node = next
	}
	tail?.next = null
	return head
}

var ll = LinkedList<Int>()
var v = intArrayOf(3, 5, 8, 5, 10, 2, 1)
for (i in v) ll.add(i)

ll.head.partition(5)!!.getList() == "1->2->3->5->8->5->10"
