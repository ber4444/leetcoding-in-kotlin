package StacksAndQueues

class Stack<E> {
    private val minCapacityIncrement = 12
    private var elements: Array<Any?> = arrayOf()
    private var size = 0

    /**
     * Pushes an element onto the stack.
     *
     * Time Complexity: Amortized O(1).
     * The array resizing operation takes O(N), but it happens infrequently.
     *
     * Space Complexity: O(N) to store the elements.
     */
    fun push(element: E) {
        if (size == elements.size) {
            val newCapacity = size + if (size < minCapacityIncrement / 2) minCapacityIncrement else (size shr 1)
            val newArray = arrayOfNulls<Any?>(newCapacity)
            System.arraycopy(elements, 0, newArray, 0, size)
            elements = newArray
        }
        elements[size++] = element
    }

    /**
     * Removes and returns the top element of the stack.
     *
     * Time Complexity: O(1).
     */
    fun pop(): Any? {
        if (size == 0) throw StackUnderflowException()
        val index = --size
        val obj = elements[index]
        elements[index] = null // Avoid memory leak
        return obj
    }

    /**
     * Returns the top element without removing it.
     *
     * Time Complexity: O(1).
     */
    fun peek() = try {
        elements[size - 1]
    } catch (e: IndexOutOfBoundsException) {
        throw StackUnderflowException()
    }

    fun isEmpty() = size == 0
}

class StackUnderflowException : RuntimeException()

// --- Testing Implementation ---
val stack = Stack<Int>()

println("--- Basic Operations ---")
stack.push(1)
stack.push(2)
println("Peek (expected 2): ${stack.peek()}")
println("Pop (expected 2): ${stack.pop()}")
println("Peek (expected 1): ${stack.peek()}")

println("\n--- Testing Underflow ---")
stack.pop() // remove 1
try {
    stack.pop()
} catch (e: StackUnderflowException) {
    println("Caught expected StackUnderflowException")
}

println("\n--- Testing Resizing (Volume Test) ---")
for (i in 0 until 20) {
    stack.push(i)
}
println("Pushed 0 to 19. Current top (expected 19): ${stack.peek()}")

var count = 0
while (!stack.isEmpty()) {
    stack.pop()
    count++
}
println("Popped $count elements. Stack empty: ${stack.isEmpty()}")
