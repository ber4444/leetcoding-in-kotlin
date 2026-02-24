package linkedLists

class Node(var data: Int) {
	var next: Node? = null
	var previous: Node? = null

	fun getList(): String = if (next != null) {
		"$data->${next?.getList()}"
	} else {
		data.toString()
	}
}

/**
 * Removes duplicates from an unsorted linked list.
 *
 * Time Complexity: O(N) - We iterate through the list exactly once.
 * Space Complexity: O(N) - We use a HashSet to store unique elements found so far.
 *
 * Note: If no temporary buffer is allowed, we can do this in O(N^2) time using two pointers
 * (current and runner).
 */
fun Node.deleteDups() {
	var current = this as Node?
	val seen = mutableSetOf<Int>()
	var previous: Node? = null

	while (current != null) {
		if (current.data in seen && previous != null) {
			previous.next = current.next
			// If strictly maintaining a doubly linked list, we should also update:
			// current.next?.previous = previous
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

// --- Tests ---

fun runTest(testName: String, setup: () -> Node) {
	println("--- $testName ---")
	val head = setup()
	println("Original: ${head.getList()}")
	head.deleteDups()
	println("Result:   ${head.getList()}")
	println()
}

// Test 1: Mixed duplicates (0->1->0->1...)
runTest("Test 1: Alternating Duplicates") {
	init()
}

// Test 2: No duplicates
runTest("Test 2: No Duplicates") {
	val head = Node(1)
	var curr = head
	for (i in 2..5) {
		curr.next = Node(i)
		curr = curr.next!!
	}
	head
}

// Test 3: All duplicates
runTest("Test 3: All Duplicates") {
	val head = Node(7)
	var curr = head
	for (i in 1..4) {
		curr.next = Node(7)
		curr = curr.next!!
	}
	head
}

// Test 4: Duplicates at the end
runTest("Test 4: Duplicates at End") {
	val head = Node(1)
	head.next = Node(2)
	head.next!!.next = Node(3)
	head.next!!.next!!.next = Node(3)
	head
}
