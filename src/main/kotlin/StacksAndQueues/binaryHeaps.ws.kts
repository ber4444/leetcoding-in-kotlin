package Trees

import java.util.PriorityQueue

// a min heap is a complete binary tree; that is, all levels of the tree, except possibly the last one (deepest) are
// fully filled, and, if the last level of the tree is not complete, the nodes of that level are filled from left to right.
// In a min heap, the value in each parent node is smaller than or equal to the values in the children of that node.
// stored as an array with root being arr[0], and any given node is at arr[i], parent is arr[(i-1)/2]
// left child is arr[(2*i)+1], right child is arr[(2*i)+2]
// arr[0] is the minimum. Inserting/removing a value is O(log n) time
val heap = PriorityQueue<Int>()
heap.add(20)
heap.add(15)
heap.add(30)
println(heap.peek())

// from scratch:
class Heap(size: Int, seed: Int) {
    private var heapSize = 0
    private val array = IntArray(size + 1).also { it[0] = seed }
    private fun parent(i: Int) = i shr 1
    private fun swap(i: Int, j: Int) { array[i] = array[j].also { array[j] = array[i] } }

    // insert to bottom right, then bubble up to the correct position
    operator fun plusAssign(value: Int) {
        require(heapSize < array.size - 1) { error("At max size") }

        array[++heapSize] = value
        var current = heapSize
        var parent = parent(current)
        while (parent != 0 && array[current] < array[parent]) {
            swap(parent, current)
            current = parent.also { parent = parent(parent) }
        }
    }
    fun peek() = if (heapSize > 0) array[1] else error("Heap is empty")
}
val heap2 = Heap(3, 0)
heap2 += 20
heap2 += 15
heap2 += 30
println(heap2.peek())
// if we'd want to know the median of a continuous flow of Ints, we could use min+max heaps