package linkedLists

import kotlin.math.abs

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

data class Result(val tail: Node, val size: Int)

fun getTailAndSize(list: Node): Result {
    var size = 1
    var current = list
    while (current.next != null) {
        size++
        current = current.next!!
    }
    return Result(current, size)
}

fun getKthNode(head: Node?, k: Int): Node? {
    var current = head
    repeat(k) {
        current = current?.next
    }
    return current
}

/**
 * Finds the intersection node of two singly linked lists.
 *
 * Time Complexity: O(A + B), where A and B are the lengths of the two linked lists.
 * We traverse each list once to find the tail and size, and then at most once more to find the intersection.
 *
 * Space Complexity: O(1).
 * We only use a few variables to store pointers and sizes.
 */
// the trick is to realize that two intersecting nodes always have the same last node
fun findIntersection(list1: Node, list2: Node): Node? {
    val tail1 = getTailAndSize(list1)
    val tail2 = getTailAndSize(list2)

    // If different tail nodes, then there's no intersection.
    if (tail1.tail !== tail2.tail) return null

    // get the head of each list
    var shorter = if (tail1.size < tail2.size) list1 else list2
    var longer = if (tail1.size < tail2.size) list2 else list1

    // chop off extra stuff from the longer list
    longer = getKthNode(longer, abs(tail1.size - tail2.size))!!

    // Move both pointers until you have a collision.
    while (shorter !== longer) {
        shorter = shorter.next!!
        longer = longer.next!!
    }
    return longer
}

// Tests
println("--- Test 1: Intersection in the middle ---")
var ll = LinkedList()
val node1 = Node(1)
val node2 = Node(2)
val node3 = Node(3)
ll.add(node1)
ll.add(node2)
ll.add(node3)
// ll: 1 -> 2 -> 3

var ll2 = LinkedList()
val node4 = Node(4)
ll2.add(node4)
// manually link to node2 (which is ll.head.next)
// ll2: 4 -> 2 -> 3
if (ll2.head != null) {
    ll2.head!!.next = node2
}

val intersection = findIntersection(ll.head!!, ll2.head!!)
println("Intersection data (expected 2): ${intersection?.data}")
println("Is correct node? ${intersection === node2}")

println("\n--- Test 2: No Intersection ---")
var l3 = LinkedList()
l3.add(Node(10))
l3.add(Node(11))
var l4 = LinkedList()
l4.add(Node(12))
l4.add(Node(13))
val intersection2 = findIntersection(l3.head!!, l4.head!!)
println("Intersection (expected null): $intersection2")

println("\n--- Test 3: Same List ---")
val intersection3 = findIntersection(ll.head!!, ll.head!!)
println("Intersection data (expected 1): ${intersection3?.data}")
println("Is correct node? ${intersection3 === node1}")

println("\n--- Test 4: Intersection at the end ---")
var l5 = LinkedList()
val nA = Node(100)
val nB = Node(101)
l5.add(nA)
l5.add(nB) // 100 -> 101

var l6 = LinkedList()
val nC = Node(200)
l6.add(nC)
if (l6.head != null) {
    l6.head!!.next = nB // 200 -> 101
}

val intersection4 = findIntersection(l5.head!!, l6.head!!)
println("Intersection data (expected 101): ${intersection4?.data}")
println("Is correct node? ${intersection4 === nB}")