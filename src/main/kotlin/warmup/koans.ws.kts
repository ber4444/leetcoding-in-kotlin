@file:Suppress("unused", "ControlFlowWithEmptyBody", "PropertyName")

package warmup

import java.util.*

@JvmField val _reader = System.`in`.bufferedReader()
// or:  readLine()!!.toInt()
// for list of ints: readLine()!!.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
// or reading unspecified amount of lines:     val lines2 = generateSequence(::readLine)
@JvmField var _tokenizer: StringTokenizer = StringTokenizer("")

fun readLines(n: Int) = List(n) { _reader.readLine()!! }
fun read(): String {
    while (_tokenizer.hasMoreTokens().not()) _tokenizer = StringTokenizer(_reader.readLine() ?: return "", " ")
    return _tokenizer.nextToken()
}
fun readInt() = read().toInt()
fun readStrings(n: Int) = List(n) { read() }
fun readInts(n: Int) = List(n) { read().toInt() } // java: similar to calling nextInt() n times
fun readIntArray(n: Int) = IntArray(n) { read().toInt() }

data class Result(val result: Int, val status: String)
fun function() = Result(1, "one")
val (result, status) = function()

val v = listOf(function())
for ((result, status) in v) { println("[$result, $status]") }
println(v.joinToString("\n")) // each element of array/list of a separate line

val map = mapOf("1" to "one")
for ((key, value) in map) { println("$key -> $value") }

if ("jane@example.com" !in map) {  }

/* when (x) {
    is Foo -> ...
    is Bar -> ...
    else   -> ...
}*/

for (x in 2..10 step 2) {  }
for (x in 10 downTo 1) {  }

abstract class MyAbstractClass {
    abstract fun doSomething()
}
fun main() {
    val myObject = object : MyAbstractClass() {
        override fun doSomething() { // ...
        }
    }
    myObject.doSomething()
}

// primitive "int[]" that avoids boxing into objects such as we'd do with Array<Int>
IntArray(10).apply { fill(-1) }
IntArray(10) { -1 }
val v1 = intArrayOf(1,2,3)
val v2 = v1.copyOf() // copy of v1 into v2
val v3 = v1.copyOfRange(0,2) // partial copy

//Swap two variables
var a = 1
var b = 2
a = b.also { b = a }

val countDown = buildString {
    for (i in 5 downTo 1) {
        append(i)
        appendLine()
    }
}
println(countDown)

val emptyList = emptyList<Int>()
val numbers = mutableListOf(1, 2, 3, 4, 5, 6)
val oddNumbers = numbers
    .filter { it % 2 != 0 }
    .joinToString{ "${-it}" }
println(oddNumbers)

val regex = Regex("""\w*\d+\w*""") // raw string
val input = "login: Pokemon5, password: 1q2w3e4r5t"
val replacementResult = regex.replace(input, replacement = "xxx")
println("Initial input: '$input'")
println("Anonymized input: '$replacementResult'")

println("Sometimes.text.should.be.split".split("."))

fun joinOptions(options: Collection<String>) =
    options.joinToString(prefix = "[", postfix = "]")

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}
operator fun MyDate.rangeTo(other: MyDate) = DateRange(this,other)
class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current: MyDate = start
            override fun next(): MyDate {
                if (!hasNext()) throw NoSuchElementException()
                val result = current
                // Assume logic to advance date exists here, e.g.:
                // current = current.plusDays(1) 
                return result
            }
            override fun hasNext(): Boolean = current <= end
        }
    }
}
fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

fun eval(expr: Expr): Int =
    when (expr) {
        is Num -> expr.value
        is Sum -> eval(expr.left) + eval(expr.right)
    }
sealed interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

val strings = listOf("bbb", "a", "cc")
strings.sorted() ==
        listOf("a", "bbb", "cc")
strings.sortedBy { it.length } ==
        listOf("a", "cc", "bbb")
strings.sortedDescending() ==
        listOf("cc", "bbb", "a")
strings.sortedWith(Comparator<String> { x, y ->
    when {
        x == "b" || y == "b" -> -1
        else -> 1
    }
})

val nums = listOf(1, -1, 2) // or setOf(...)
nums - listOf(1,-1) == listOf(2)
nums.filter { it > 0 } == listOf(1, 2)
nums.map { it * it } == listOf(1, 1, 4) // transforming all elements of a list
nums.mapIndexed { idx, value ->
    if (idx == 0) value + 1 else value + 2
}

data class Shop(val name: String, val customers: List<Customer>)
data class Customer(val name: String, val city: City, val orders: List<Order>)
data class Order(val products: List<Product>)
data class Product(val name: String, val price: Double)
data class City(val name: String)
val sampleCustomer = Customer(
    "Alice",
    City("London"),
    listOf(
        Order(listOf(Product("Apple", 2.0), Product("Banana", 1.5))),
        Order(listOf(Product("Orange", 3.0)))
    )
)
val customers = listOf(sampleCustomer)

// Find all the different cities the customers are from
fun Shop.getCustomerCities(): Set<City> = customers.map { it.city }.toSet()

val intList = listOf(1, 42, 4)
intList.maxOrNull() == 42
intList.sorted().binarySearch(42) // O(log n), works only if the list is sorted
// refresher on how it works under the hood: https://github.com/ditn/interviewprep/blob/main/Algorithms/Topics/Binary%20Search.md
intList.indexOf(42)
intList.find { it > 40 } // finds first element greater than 40, or null if not found
intList.findLast { it < 40 }
listOf("a", "ab").minByOrNull(String::length) == "a"
val maxPrice = sampleCustomer.orders.flatMap(Order::products).maxByOrNull(Product::price)
sampleCustomer.orders.flatMap { it.products }.sumOf { it.price }

val list = listOf("abc", "cdef")
list.associateWith { it.length } ==
        mapOf("abc" to 3, "cdef" to 4)
list.associate { it.first() to it.length } ==
        mapOf('a' to 3, 'c' to 4)
//// Build a map from the customer name to the customer
customers.associateBy(Customer::name)

val res =
    listOf("a", "b", "ba", "ccc", "ad")
        .groupBy { it.length }
res == mapOf(
    1 to listOf("a", "b"),
    2 to listOf("ba", "ad"),
    3 to listOf("ccc"))
//// Build a map that stores the customers living in a given city
customers.groupBy { it.city }

listOf("abc", "12").flatMap { it.toList() } == listOf('a', 'b', 'c', '1', '2')
//// Return all products the given customer has ordered
sampleCustomer.orders.flatMap(Order::products)

//val maximumSizeOfGroup = groupsByLength.values.map { group -> group.size }.maxOrNull()

val isOdd: Int.() -> Boolean = { this % 2 != 0 }
42.isOdd()

val names = listOf("Chandra", "Rivu", "Nick", "Ahmed")
val ages = listOf(30, 27, 35, 19)
println(names.zip(ages))