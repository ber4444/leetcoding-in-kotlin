package StacksAndQueues

/**
 * Stack implementation using a Doubly Linked List.
 *
 * Space Complexity: O(n) where n is the number of elements in the stack.
 */
class Stack<E> {
    private var size = 0
    private var head: Node<E>? = null
    private var tail: Node<E>? = null

    private inner class Node<E>(var prev: Node<E>?, var element: E, var next: Node<E>?)

    /**
     * Pushes an element onto the stack.
     * Time Complexity: O(1)
     */
    fun push(element: E) {
        val currentTail = tail
        val newNode = Node(currentTail, element, null)
        tail = newNode
        if (currentTail == null) {
            head = newNode
        } else {
            currentTail.next = newNode
        }
        size++
    }

    /**
     * Pops an element from the stack.
     * Time Complexity: O(1)
     */
    fun pop(): E {
        val currentTail = tail ?: throw StackUnderflowException()
        val prev = currentTail.prev
        tail = prev
        if (prev == null) {
            head = null
        } else {
            prev.next = null
        }
        size--
        return currentTail.element
    }

    /**
     * Peeks at the top element of the stack without removing it.
     * Time Complexity: O(1)
     */
    fun peek(): E = tail?.element ?: throw StackUnderflowException()

    /**
     * Checks if the stack is empty.
     * Time Complexity: O(1)
     */
    fun isEmpty() = size == 0
}

class StackUnderflowException : RuntimeException("Stack is empty")

// Testing the implementation
val stack = Stack<Int>()

println("--- Testing Stack Operations ---")
println("Is stack empty? ${stack.isEmpty()}") // Expected: true

println("Pushing 1")
stack.push(1)
println("Pushing 2")
stack.push(2)
println("Pushing 3")
stack.push(3)

println("Peek top element: ${stack.peek()}") // Expected: 3
println("Is stack empty? ${stack.isEmpty()}") // Expected: false

println("Popped: ${stack.pop()}") // Expected: 3
println("Peek after pop: ${stack.peek()}") // Expected: 2

println("Popped: ${stack.pop()}") // Expected: 2
println("Popped: ${stack.pop()}") // Expected: 1

println("Is stack empty? ${stack.isEmpty()}") // Expected: true

try {
    println("Attempting to peek empty stack...")
    stack.peek()
} catch (e: StackUnderflowException) {
    println("Caught expected exception on peek: ${e.message}")
}

try {
    println("Attempting to pop empty stack...")
    stack.pop()
} catch (e: StackUnderflowException) {
    println("Caught expected exception on pop: ${e.message}")
}
