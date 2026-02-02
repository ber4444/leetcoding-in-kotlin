package StacksAndQueuesCtCi

class Stack<E> {
    private val minCapacityIncrement = 12
    private var elements: Array<Any?> = arrayOf()
    private var size = 0

    fun push(element: E) {
        if (size == elements.size) {
            val newCapacity = size + if (size < minCapacityIncrement / 2) minCapacityIncrement else (size shr 1)
            val newArray = arrayOfNulls<Any?>(newCapacity)
            System.arraycopy(elements, 0, newArray, 0, size)
            elements = newArray
        }
        elements[size++] = element
    }

    fun pop(): Any? {
        if (size == 0) throw StackUnderflowException()
        val index = --size
        val obj = elements[index]
        elements[index] = null
        return obj
    }

    fun peek() = try {
        elements[size - 1]
    } catch (e: IndexOutOfBoundsException) {
        throw StackUnderflowException()
    }
}

class StackUnderflowException : RuntimeException()

val stack = Stack<Int>()
stack.push(1)
stack.push(2)
println(stack.peek())
println(stack.pop())
println(stack.peek())
