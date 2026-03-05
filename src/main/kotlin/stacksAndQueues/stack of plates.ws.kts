package stacksAndQueues

import java.util.*

/**
 * SetOfStacks implementation.
 *
 * Time Complexity:
 * - push: O(1) - Pushing to a stack is O(1). Creating a new stack is amortized O(1).
 * - pop: O(1) - Popping from a stack is O(1). Removing an empty stack from the list is O(1) since it's the last one.
 *
 * Space Complexity:
 * - O(N) where N is the total number of elements. The overhead of stack objects is negligible compared to the elements.
 */
//  a Stack with a limit that creates a new Stack when the limit is reached
// push and pop is applied to the whole set as if there was a single stack in it
class SetOfStacks(var capacity: Int) {
	val stacks = mutableListOf<Stack<Int>>()

	private val lastStack: Stack<Int>?
		get() = stacks.lastOrNull()

	// call push() on the last stack in the array of stacks (unless that is at capacity in which case
	// we need to create a new stack)
	fun push(v: Int) {
		val last = lastStack
		if (last != null && last.size < capacity) {
			last.push(v)
		} else {
			val stack = Stack<Int>()
			stack.push(v)
			stacks.add(stack)
		}
	}

	// pop() from the last stack and if it becomes empty, remove it from the array
	fun pop(): Int {
		val last = lastStack ?: throw EmptyStackException()
		val v = last.pop()
		if (last.isEmpty()) stacks.removeAt(stacks.size - 1)
		return v
	}

	val isEmpty: Boolean
		get() = lastStack?.isEmpty() ?: true
}

// Tests
val setOfStacks = SetOfStacks(2)
println("Is empty initially: ${setOfStacks.isEmpty}")

setOfStacks.push(1)
setOfStacks.push(2)
println("After pushing 1, 2. Number of stacks: ${setOfStacks.stacks.size} (Expected: 1)")

setOfStacks.push(3)
println("After pushing 3. Number of stacks: ${setOfStacks.stacks.size} (Expected: 2)")

val popped1 = setOfStacks.pop()
println("Popped: $popped1 (Expected: 3)")
println("After pop. Number of stacks: ${setOfStacks.stacks.size} (Expected: 1)")

val popped2 = setOfStacks.pop()
println("Popped: $popped2 (Expected: 2)")

val popped3 = setOfStacks.pop()
println("Popped: $popped3 (Expected: 1)")

println("Is empty after all pops: ${setOfStacks.isEmpty} (Expected: true)")

try {
    setOfStacks.pop()
    println("Error: Should have thrown EmptyStackException")
} catch (e: EmptyStackException) {
    println("Success: Caught expected EmptyStackException")
}