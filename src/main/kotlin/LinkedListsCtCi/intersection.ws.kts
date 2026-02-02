package LinkedListsCtCi

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

// if 2 singly linked lists intersect, return the intersecting node (as determined by reference, not by value)
// the trick is to realize that two intersecting nodes always have the same last node
// traverse the 2 lists in parallel and look at where they collide (chop off the excess nodes from the start if one is longer)
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

var ll = LinkedList()
ll.add(Node(1))
ll.add(Node(2))
ll.add(Node(3))
var ll2 = LinkedList()
ll2.add(Node(4))
ll2.head?.next = ll.head?.next!!
println(findIntersection(ll.head!!, ll2.head!!)?.data)
