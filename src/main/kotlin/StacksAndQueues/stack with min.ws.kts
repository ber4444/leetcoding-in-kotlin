package StacksAndQueuesCtCi

import java.util.*

class StackWithMin : Stack<Int>() {
	private val minStack = Stack<Int>()

	override fun push(value: Int): Int {
		if (value <= min()) {
			minStack.push(value)
		}
		return super.push(value)
	}

	override fun pop(): Int? {
		val value = super.pop()
		if (value == min()) {
			minStack.pop()
		}
		return value
	}

	// O(1) time for returning min value in stack
	fun min(): Int = if (minStack.isEmpty()) Int.MAX_VALUE else minStack.peek()
}

val stack = StackWithMin()
stack.push(5)
stack.push(3)
stack.push(2)
stack.push(6)
println(stack.min())
stack.pop()
stack.pop()
println(stack.min())