package StacksAndQueuesCtCi

import java.util.Queue
import java.util.LinkedList

// difference between array and linkedlist: in a linkedlist, you cannot access elements by index
// however, adding/removing to the head of a list (queue) is O(1) time, while in an array it would be O(n)
val queue: Queue<String> = LinkedList()

queue.add("apple")
queue.add("banana")

println(queue)
println("first: ${queue.peek()}")
println(queue.contains("apple"))
println(queue.size)
println(queue.isEmpty())

////// difference between array and arraylist: the latter is resizable
val vector = ArrayList<Int>()
vector.add(0)
vector.add(1)
vector.add(2)
println(vector[2])
val arr = Array(4) { 0 } // or arrayOf(0,0,0,0)
arr[3]

// listOf() creates immutable list
// mutableListOf() or arrayListOf() creates mutable list
