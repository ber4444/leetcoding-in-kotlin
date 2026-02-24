package StacksAndQueues

// Space Complexity: O(N) where N is the number of elements in the queue
class Queue<E> {
    private val minCapacityIncrement = 12
    private var elements: Array<Any?> = arrayOf<Any?>()
    private var size = 0

    constructor()

    constructor(initialCapacity: Int) {
        elements = arrayOfNulls<Any?>(initialCapacity)
    }

    // Time Complexity: Amortized O(1). Worst case O(N) when resizing occurs.
    fun enqueue(element: E) {
        if (size == elements.size) {
            val newCapacity = size + if (size < minCapacityIncrement / 2) minCapacityIncrement else (size shr 1)
            val newArray = arrayOfNulls<Any?>(newCapacity)
            System.arraycopy(elements, 0, newArray, 0, size)
            elements = newArray
        }
        elements[size++] = element
    }

    // Time Complexity: O(N) because shifting elements is required.
    fun dequeue(): E {
        if (size == 0) throw QueueUnderflowException()
        val oldVal = elements[0]
        elements[0] = null // Prevent memory leak
        System.arraycopy(elements, 1, elements, 0, --size)
        return oldVal as E
    }

    // Time Complexity: O(1)
    fun front() = try {
        elements[0] as E
    } catch (e: IndexOutOfBoundsException) {
        throw QueueUnderflowException()
    }

    // Time Complexity: O(1)
    fun rear() = try {
        elements[size - 1] as E
    } catch (e: IndexOutOfBoundsException) {
        throw QueueUnderflowException()
    }

    // Time Complexity: O(1)
    fun isEmpty() = size == 0

    // Time Complexity: O(1)
    fun isFull() = size == elements.size
}

class QueueUnderflowException : RuntimeException("Queue is empty")

// --- Tests ---

println("--- Basic Operations Test ---")
val queue = Queue<Int>(2)
queue.enqueue(1)
queue.enqueue(2)
println("Front (should be 1): ${queue.front()}")
println("Rear (should be 2): ${queue.rear()}")
println("Is Full (should be true): ${queue.isFull()}")

val dequeued1 = queue.dequeue()
println("Dequeued: $dequeued1")
println("Next to dequeue: ${queue.front()}")
println("Is Empty (should be false): ${queue.isEmpty()}")

val dequeued2 = queue.dequeue()
println("Dequeued: $dequeued2")
println("Is Empty (should be true): ${queue.isEmpty()}")

println("\n--- Resizing Test ---")
val dynamicQueue = Queue<String>(2)
dynamicQueue.enqueue("A")
dynamicQueue.enqueue("B")
println("Capacity reached. Adding 'C' to trigger resize...")
dynamicQueue.enqueue("C")
println("Enqueued C. Front: ${dynamicQueue.front()}, Rear: ${dynamicQueue.rear()}")
println("Size matches expected? ${!dynamicQueue.isEmpty()}")

println("\n--- Exception Test ---")
try {
    val emptyQ = Queue<Int>()
    emptyQ.dequeue()
} catch (e: QueueUnderflowException) {
    println("Success: Caught QueueUnderflowException")
}
