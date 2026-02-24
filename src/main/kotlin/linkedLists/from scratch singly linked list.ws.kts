package linkedLists

// an ArrayList or ArrayDeque is advised over a LinkedList, they use less RAM and are faster

class LinkyList<E> {
    var size = 0
        private set
    private var head: Node<E>? = null
    private var tail: Node<E>? = null

    private inner class Node<E> constructor(var element: E, var next: Node<E>?)

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    fun getFirst() = head?.element

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    fun getLast() = tail?.element

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    fun prepend(element: E) {
        val h = head
        val newNode = Node(element, h)
        head = newNode
        if (h == null) {
            tail = newNode
        }
        size++
    }

    /**
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    fun append(element: E) {
        val t = tail
        val newNode = Node(element, null)
        tail = newNode
        if (t == null) {
            head = newNode
        } else {
            t.next = newNode
        }
        size++
    }

    /**
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    fun deleteWithValue(element: E) {
        if (head == null) return

        if (head?.element == element) {
            head = head?.next
            if (head == null) {
                tail = null
            }
            size--
            return
        }

        var current = head
        while (current?.next != null) {
            if (current.next?.element == element) {
                if (current.next == tail) {
                    tail = current
                }
                current.next = current.next?.next
                size--
                return
            }
            current = current.next
        }
    }

    fun toList(): List<E> {
        val list = mutableListOf<E>()
        var current = head
        while (current != null) {
            list.add(current.element)
            current = current.next
        }
        return list
    }
}

// Testing the implementation
val sLL = LinkyList<Char>()
println("Initial list: ${sLL.toList()}, Size: ${sLL.size}")

sLL.append('b')
println("Appended 'b': ${sLL.toList()}, First: ${sLL.getFirst()}, Last: ${sLL.getLast()}, Size: ${sLL.size}")

sLL.prepend('a')
println("Prepended 'a': ${sLL.toList()}, First: ${sLL.getFirst()}, Last: ${sLL.getLast()}, Size: ${sLL.size}")

sLL.append('c')
println("Appended 'c': ${sLL.toList()}, First: ${sLL.getFirst()}, Last: ${sLL.getLast()}, Size: ${sLL.size}")

sLL.deleteWithValue('a')
println("Deleted 'a' (head): ${sLL.toList()}, First: ${sLL.getFirst()}, Last: ${sLL.getLast()}, Size: ${sLL.size}")

sLL.deleteWithValue('c')
println("Deleted 'c' (tail): ${sLL.toList()}, First: ${sLL.getFirst()}, Last: ${sLL.getLast()}, Size: ${sLL.size}")

sLL.deleteWithValue('b')
println("Deleted 'b' (last element): ${sLL.toList()}, First: ${sLL.getFirst()}, Last: ${sLL.getLast()}, Size: ${sLL.size}")

// Test with non-existent element
sLL.append('x')
sLL.deleteWithValue('z')
println("After deleting non-existent 'z' from ['x']: ${sLL.toList()}, Size: ${sLL.size}")
