package linkedLists

class Node(var data: Int) {
	var next: Node? = null

	fun linkedListToInt(): Int {
		var value = 0
		next?.let {
			value = 10 * it.linkedListToInt()
		}
		return value + data
	}
}

class LinkedList {
	var head: Node? = null

	fun add(value: Int) {
		val newNode = Node(value)
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

// e.g. 6->1->7 + 2->9->5 = 9->1->2
// (note: head of the 1st list is 7, that is what we add first)
// add node by node and carry the excess

// Time Complexity: O(max(N, M)) where N and M are lengths of the lists
// Space Complexity: O(max(N, M)) for recursion stack and new nodes
fun addLists(n1: Node?, n2: Node?, carry: Int = 0): Node? {
	if (n1 == null && n2 == null && carry == 0) return null

	val value = carry + (n1?.data ?: 0) + (n2?.data ?: 0)
	val result = Node(value % 10)
	result.next = addLists(n1?.next, n2?.next, value / 10)
	return result
}

fun Node.addLists(other: Node?, carry: Int = 0): Node? {
	return addLists(this, other, carry)
}

val ll = LinkedList()
val ll2 = LinkedList()
ll.add(7)
ll.add(1)
ll.add(6)
ll2.add(5)
ll2.add(9)
ll2.add(2)
println("7->1->6 + 5->9->2 = 2->1->9 (912)")
println(ll.head?.addLists(ll2.head)?.linkedListToInt())

// Test with different lengths
val l3 = LinkedList()
l3.add(1) // 1
val l4 = LinkedList()
l4.add(9) 
l4.add(9) // 9->9 (99)
// 1 + 99 = 100 -> 0->0->1
println("1 + 9->9 = 0->0->1 (100)")
val res2 = addLists(l3.head, l4.head)
println(res2?.linkedListToInt()) // Should be 100

// Test with carry overflow at end
val l5 = LinkedList()
l5.add(5)
val l6 = LinkedList()
l6.add(5)
println("5 + 5 = 0->1 (10)")
val res3 = addLists(l5.head, l6.head)
println(res3?.linkedListToInt()) // Should be 10