package LinkedListsCtCi

// reverse the list, then check if the first half is same as the original
val ll = listOf(0, 1, 2, 1, 0)
val ll2 = ll.reversed()
ll.subList(0, ll.size/2) == ll2.subList(0, ll2.size/2)
