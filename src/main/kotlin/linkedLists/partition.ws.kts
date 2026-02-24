package linkedLists

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
			node = node.next
		}
		return node
	}
}

/**
 * Partition a linked list so that all nodes < x come before nodes >= x.
 * No need to sort partitions.
 *
 * Time Complexity: O(N) - We iterate through the linked list exactly once.
 * Space Complexity: O(1) - We modify the list in place using a fixed number of pointers (head, tail, node, next).
 */
fun Node<Int>?.partition(x: Int): Node<Int>? {
    // If the list is empty or has only one element, return it as is.
    if (this == null || this.next == null) return this

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

// Test Case 1: Provided example
var ll = LinkedList<Int>()
var v = intArrayOf(3, 5, 8, 5, 10, 2, 1)
for (i in v) ll.add(i)

println("Original: ${ll.head?.getList()}")
val partitioned = ll.head.partition(5)
println("Partitioned (x=5): ${partitioned?.getList()}")

// Verify result: items < 5 should be before items >= 5
// Based on the unstable algorithm (prepend < x), the order of < x elements will be reversed relative to original.
// 3, 2, 1 -> 1, 2, 3
// Items >= 5: 5, 8, 5, 10 -> preserved order -> 5, 8, 5, 10
// Result should be 1->2->3->5->8->5->10
val expected = "1->2->3->5->8->5->10"
val result = partitioned?.getList() == expected
println("Test Passed: $result")

// Test Case 2: All elements < x
var ll2 = LinkedList<Int>()
for (i in intArrayOf(3, 2, 1)) ll2.add(i)
// Prepend logic: 3 comes first (head=3), then 2 (head=2, 2->3), then 1 (head=1, 1->2->3).
// Result: 1->2->3
val p2 = ll2.head.partition(5)
println("All < 5: ${p2?.getList()}")
println("Test 2 Passed: ${p2?.getList() == "1->2->3"}")

// Test Case 3: All elements >= x
var ll3 = LinkedList<Int>()
for (i in intArrayOf(6, 7, 8)) ll3.add(i)
// Append logic: 6 (tail=6), 7 (tail=7, 6->7), 8 (tail=8, 6->7->8)
// Result: 6->7->8
val p3 = ll3.head.partition(5)
println("All >= 5: ${p3?.getList()}")
println("Test 3 Passed: ${p3?.getList() == "6->7->8"}")

// Test Case 4: Single Element
var ll4 = LinkedList<Int>()
ll4.add(10)
val p4 = ll4.head.partition(5)
println("Single Element: ${p4?.getList()}")
