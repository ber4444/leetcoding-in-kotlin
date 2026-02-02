package LinkedListsCtCi

class LinkyList<E> {
    private var size = 0
    private var head: Node<E>? = null
    private var tail: Node<E>? = null

    private inner class Node<E> constructor(var element: E, var next: Node<E>?)

    fun getFirst() = head?.element
    fun getLast() = tail?.element

    fun prepend(element: E) {
        val h = head
        val newNode = Node(element, h)
        head = newNode
        if (h == null) {
            tail = newNode
        }
        size++
    }

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

    fun deleteWithValue(element: E) {
        if (head?.element == element) {
            head = head?.next
            return
        }

        var current = head
        while (current?.next != null) {
            if (current.next?.element == element) {
                current.next = current.next?.next
                return
            }
            current = current.next
        }
    }
}
val sLL = LinkyList<Char>()
sLL.append('b')
println(sLL.getFirst())
sLL.prepend('a')
sLL.append('c')
println(sLL.getFirst())
println(sLL.getLast())
sLL.deleteWithValue('a')
sLL.deleteWithValue('b')
println(sLL.getFirst())
