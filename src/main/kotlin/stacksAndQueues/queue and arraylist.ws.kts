package stacksAndQueues

import java.util.Queue
import java.util.LinkedList

// Difference between Array and LinkedList:
// - LinkedList:
//   - Accessing elements by index is O(n).
//   - Adding/removing to the head/tail (queue operations) is O(1) time.
// - Array/ArrayList:
//   - Accessing elements by index is O(1).
//   - Adding/removing from the beginning or middle is O(n) due to shifting.

// Space Complexity: O(n) for both (where n is the number of elements).

val queue: Queue<String> = LinkedList()

println("--- Queue Tests ---")
queue.add("apple")
queue.add("banana")
queue.add("cherry")

println("Queue: $queue")
println("Peek first: ${queue.peek()}") // O(1)
println("Contains 'apple': ${queue.contains("apple")}") // O(n)
println("Size: ${queue.size}") // O(1)
println("IsEmpty: ${queue.isEmpty()}") // O(1)

// Test removal (dequeue)
val removedItem = queue.remove() // O(1)
println("Removed item: $removedItem")
println("Queue after removal: $queue")
println("New head: ${queue.peek()}")


println("\n--- ArrayList/Array Tests ---")
// ArrayList: Resizable array implementation of the List interface
// - add (append): Amortized O(1)
// - get(index): O(1)
// - remove(index): O(n)
// - contains: O(n)

val vector = ArrayList<Int>()
vector.add(0)
vector.add(1)
vector.add(2)

println("ArrayList: $vector")
println("Element at index 2: ${vector[2]}") // O(1) access

vector.removeAt(0) // O(n) - shifts subsequent elements
println("ArrayList after removing element at 0: $vector")

// Array: Fixed size
val arr = Array(4) { 0 } // or arrayOf(0, 0, 0, 0)
arr[3] = 10
println("Fixed Array: ${arr.joinToString()}")
println("Array element at 3: ${arr[3]}") // O(1) access

// Immutable List
val immutableList = listOf("a", "b", "c")
println("Immutable List: $immutableList")
// immutableList.add("d") // Compilation error: cannot add to immutable list

// Mutable List using factory functions
val mutableList = mutableListOf("x", "y")
mutableList.add("z")
println("Mutable List: $mutableList")