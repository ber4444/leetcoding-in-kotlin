package StacksAndQueuesCtCi

import java.util.*

// implement SetOfStacks - a Stack with a limit that creates a new Stack when the limit is reached
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

val setOfStacks = SetOfStacks(2)
setOfStacks.push(1)
setOfStacks.push(2)
setOfStacks.push(3)
println(setOfStacks.stacks.size)
setOfStacks.pop()
println(setOfStacks.stacks.size)
setOfStacks.pop()
setOfStacks.pop()
setOfStacks.isEmpty