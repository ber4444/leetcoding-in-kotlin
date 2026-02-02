package LinkedListsCtCi

class Node(var data: Int) {
	var next: Node? = null
	var previous: Node? = null

	fun getList(): String = if (next != null) {
		"$data->${next?.getList()}"
	} else {
		data.toString()
	}
}

// O(N) time solution for: given an unsorted linked list, remove dupes
// (if no HashSet is allowed, we can do it in O(n^2) time only by using 2 pointers -
//   `current` which iterates through the list, and `runner` which checks subsequent nodes for dupes)
fun Node.deleteDups() {
	var current = this as Node?
	val seen = mutableSetOf<Int>()
	var previous: Node? = null

	while (current != null) {
		if (current.data in seen && previous != null) {
			previous.next = current.next
		} else {
			seen.add(current.data)
			previous = current
		}
		current = current.next
	}
}

fun init(): Node {
	var first = Node(0)
	val head = first
	for (i in 1..8) {
		val second = Node(i % 2)
		first.next = second
		second.previous = first
		first = second
	}
	return head
}

val head = init()
println(head.getList())
head.deleteDups()
println(head.getList())

