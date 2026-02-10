package StacksAndQueuesCtCi

class Queue<E> {
    private val minCapacityIncrement = 12
    private var elements: Array<Any?> = arrayOf()
    private var size = 0

    constructor()

    constructor(initialCapacity: Int) {
        elements = arrayOfNulls(initialCapacity)
    }

    fun enqueue(element: E) {
        if (size == elements.size) {
            val newCapacity = size + if (size < minCapacityIncrement / 2) minCapacityIncrement else (size shr 1)
            val newArray = arrayOfNulls<Any>(newCapacity)
            System.arraycopy(elements, 0, newArray, 0, size)
            elements = newArray
        }
        elements[size++] = element
    }

    fun dequeue(): E {
        if (size == 0) throw QueueUnderflowException()
        val oldVal = elements[0]
        elements[0] = null
        System.arraycopy(elements, 1, elements, 0, --size)
        return oldVal as E
    }

    fun front() = try {
        elements[0] as E
    } catch (e: IndexOutOfBoundsException) {
        throw QueueUnderflowException()
    }

    fun rear() = try {
        elements[size - 1] as E
    } catch (e: IndexOutOfBoundsException) {
        throw QueueUnderflowException()
    }

    fun isEmpty() = size == 0

    fun isFull() = size == elements.size
}

class QueueUnderflowException : RuntimeException()

val queue = Queue<Int>(2)
queue.enqueue(1)
queue.enqueue(2)
println(queue.front())
println(queue.rear())
println(queue.isFull())
queue.dequeue()
println(queue.dequeue())
println(queue.isEmpty())

