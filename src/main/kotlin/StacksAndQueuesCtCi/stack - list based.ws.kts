package StacksAndQueuesCtCi

class Stack<E> {
    private var size = 0
    private var head: Node<E>? = null
    private var tail: Node<E>? = null

    private inner class Node<E>(var prev: Node<E>?, var element: E, var next: Node<E>?)

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

    fun peek(): E = tail?.element ?: throw StackUnderflowException()

    fun isEmpty() = size == 0
}

class StackUnderflowException : RuntimeException()

val stack = Stack<Int>()
stack.push(1)
stack.push(2)
println(stack.peek())
println(stack.pop())
println(stack.peek())
println(stack.isEmpty())
