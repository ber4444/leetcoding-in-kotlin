package StacksAndQueues

import java.util.*

/**
 * Stack implementation that supports push, pop, min, and max operations.
 *
 * Time Complexity:
 * - push: O(1)
 * - pop: O(1)
 * - min: O(1)
 * - max: O(1)
 *
 * Space Complexity: O(N)
 * - We use two additional stacks (minStack and maxStack) to keep track of the minimum and maximum values.
 * - In the worst case (e.g., elements inserted in sorted order), the auxiliary stacks can grow up to size N.
 */
class StackWithMin : Stack<Int>() {
    private val minStack = Stack<Int>()
    private val maxStack = Stack<Int>()

    override fun push(value: Int): Int {
        if (value <= min()) {
            minStack.push(value)
        }
        if (value >= max()) {
            maxStack.push(value)
        }
        return super.push(value)
    }

    override fun pop(): Int? {
        if (isEmpty()) return null
        val value = super.pop()
        if (value == min()) {
            minStack.pop()
        }
        if (value == max()) {
            maxStack.pop()
        }
        return value
    }

    // O(1) time for returning min value in stack
    fun min(): Int = if (minStack.isEmpty()) Int.MAX_VALUE else minStack.peek()

    // O(1) time for returning max value in stack
    fun max(): Int = if (maxStack.isEmpty()) Int.MIN_VALUE else maxStack.peek()
}

// --- Testing the implementation ---

val stack = StackWithMin()

println("--- Push 5, 3, 2, 6 ---")
stack.push(5)
stack.push(3)
stack.push(2)
stack.push(6)
println("Stack: $stack")
println("Min: " + stack.min()) // Expected: 2
println("Max: " + stack.max()) // Expected: 6

println("\n--- Pop once (removing 6) ---")
stack.pop()
println("Stack: $stack")
println("Min: " + stack.min()) // Expected: 2
println("Max: " + stack.max()) // Expected: 5

println("\n--- Pop once (removing 2) ---")
stack.pop()
println("Stack: $stack")
println("Min: " + stack.min()) // Expected: 3
println("Max: " + stack.max()) // Expected: 5

println("\n--- Testing Duplicates: Push 3, 3 ---")
stack.push(3)
stack.push(3)
println("Stack: $stack")
println("Min: " + stack.min()) // Expected: 3
println("Max: " + stack.max()) // Expected: 5

println("\n--- Pop once (removing one 3) ---")
stack.pop()
println("Stack: $stack")
println("Min: " + stack.min()) // Expected: 3

println("\n--- Pop once (removing second 3) ---")
stack.pop()
println("Stack: $stack")
println("Min: " + stack.min()) // Expected: 3
