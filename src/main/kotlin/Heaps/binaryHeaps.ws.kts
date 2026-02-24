package Heaps

import java.util.PriorityQueue

// a min heap (binary heap) is a complete binary tree where every child belongs to a parent node that has a greater/smaller value (max/min heap)
// lookup O(1) insert O(log n) delete O(log n)
// stored as an array with root being arr[1], and any given node is at arr[i], parent is arr[i/2]
// left child is arr[2*i], right child is arr[2*i+1]
// Space Complexity: O(n)

val heap = PriorityQueue<Int>()
heap.add(20)
heap.add(15)
heap.add(30)
println("PriorityQueue Peek: ${heap.peek()}")

// from scratch:
// Using 1-based indexing
class Heap(size: Int) {
    private var heapSize = 0
    private val array = IntArray(size + 1) // index 0 unused
    
    private fun parent(i: Int) = i shr 1
    private fun left(i: Int) = i shl 1
    private fun right(i: Int) = (i shl 1) + 1
    
    private fun swap(i: Int, j: Int) { array[i] = array[j].also { array[j] = array[i] } }

    // Time: O(log n)
    // insert to bottom right, then bubble up to the correct position
    operator fun plusAssign(value: Int) {
        require(heapSize < array.size - 1) { error("At max size") }

        array[++heapSize] = value
        var current = heapSize
        // Bubble up (Swim)
        // Check current > 1 to avoid comparing with index 0
        while (current > 1 && array[current] < array[parent(current)]) {
            swap(parent(current), current)
            current = parent(current)
        }
    }
    
    // Time: O(1)
    fun peek() = if (heapSize > 0) array[1] else error("Heap is empty")
    
    // Time: O(n)
    fun isValidMinHeap(): Boolean {
        for (i in 1..heapSize / 2) {
            val l = left(i)
            val r = right(i)
            if (l <= heapSize && array[i] > array[l]) return false
            if (r <= heapSize && array[i] > array[r]) return false
        }
        return true
    }

    // Time: O(n)
    fun isValidMaxHeap(): Boolean {
        for (i in 1..heapSize / 2) {
            val l = left(i)
            val r = right(i)
            if (l <= heapSize && array[i] < array[l]) return false
            if (r <= heapSize && array[i] < array[r]) return false
        }
        return true
    }

    // Time: O(n)
    fun toMaxHeap() {
        for (i in heapSize / 2 downTo 1) {
            var k = i
            while (left(k) <= heapSize) {
                var j = left(k)
                // Find larger child
                if (j < heapSize && array[j] < array[j + 1]) j++
                
                if (array[k] >= array[j]) break
                swap(k, j)
                k = j
            }
        }
    }
}

// Tests
val heap2 = Heap(10)
heap2 += 2
heap2 += 5
heap2 += 23
heap2 += 100
heap2 += 250

println("--- Min Heap Tests ---")
println("Peek: ${heap2.peek()}") // 2
println("Valid MinHeap: ${heap2.isValidMinHeap()}") // true

heap2 += 1
println("Added 1")
println("Peek: ${heap2.peek()}") // 1
println("Valid MinHeap: ${heap2.isValidMinHeap()}") // true

println("--- Max Heap Conversion ---")
heap2.toMaxHeap()
println("Peek: ${heap2.peek()}") // 250
println("Valid MaxHeap: ${heap2.isValidMaxHeap()}") // true
println("Valid MinHeap: ${heap2.isValidMinHeap()}") // false

// if we'd want to know the median of a continuous flow of Ints, we could use min+max heaps
