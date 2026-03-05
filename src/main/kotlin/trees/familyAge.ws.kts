package trees

// calculate total sum of ages in a family tree
// Person objects are stored in a BST

data class Person(
    val name: String,
    val age: Int
) : Comparable<Person> {
    override fun compareTo(other: Person): Int {
        return this.age.compareTo(other.age)
    }
}

class Node(val value: Person) {
    var left: Node? = null
    var right: Node? = null

    // Time Complexity: O(H), where H is the height of the tree.
    //   - Worst case (skewed tree): O(N)
    //   - Average case (balanced tree): O(log N)
    // Space Complexity: O(H) due to the recursion stack.
    fun insert(p: Person) {
        if (p < value) {
            if (left == null) left = Node(p)
            else left!!.insert(p)
        } else {
            if (right == null) right = Node(p)
            else right!!.insert(p)
        }
    }

    // Time Complexity: O(N), where N is the total number of nodes in the tree (we visit every node).
    // Space Complexity: O(H), where H is the height of the tree, for the recursion stack.
    fun sumOfAges(): Int {
        var sum = value.age
        left?.let { sum += it.sumOfAges() }
        right?.let { sum += it.sumOfAges() }
        return sum
    }
}

println("--- Test 1: Basic Family Tree ---")
val family = Node(Person("Adam", 5))
family.insert(Person("Eve", 2))
family.insert(Person("Cain", 1))

// Tree Structure:
//      Adam(5)
//      /
//    Eve(2)
//    /
// Cain(1)

val sum1 = family.sumOfAges()
println("Sum of ages (Expected 8): $sum1")

println("\n--- Test 2: Extending the Family ---")
family.insert(Person("Abel", 4))
family.insert(Person("Seth", 6))

// Updated Tree Structure:
//       Adam(5)
//       /    \
//    Eve(2)  Seth(6)
//    /    \
// Cain(1) Abel(4)

val sum2 = family.sumOfAges()
println("Sum of ages (Expected 8 + 4 + 6 = 18): $sum2")

println("\n--- Test 3: Skewed Tree ---")
val skewed = Node(Person("Gen1", 10))
skewed.insert(Person("Gen2", 20))
skewed.insert(Person("Gen3", 30))

// Tree Structure:
// Gen1(10)
//   \
//   Gen2(20)
//     \
//     Gen3(30)

val sum3 = skewed.sumOfAges()
println("Sum of skewed tree (Expected 60): $sum3")
