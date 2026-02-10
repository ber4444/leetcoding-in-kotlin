// 1. adapter (converts interface to another interface, or converts data between formats)
// was popular in Android's View-based UI, no longer used in Compose
// 3rd party lib:
data class DisplayDataType(val index: Int, val data: String)
class DataDisplay {
    fun displayData(data: List<DisplayDataType>) = println(data)
}
// app code:
data class DatabaseData(val foo: String, val bar: String)
class DatabaseDataGenerator {
    fun generateData(): List<DatabaseData> = listOf(
        DatabaseData("foo1", "bar1"),
        DatabaseData("foo2", "bar2"),
    )
}
interface DatabaseDataConverter {
    fun convertData(data: List<DatabaseData>): List<DisplayDataType>
}
class DataDisplayAdapter(val display: DataDisplay) : DatabaseDataConverter {
    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
        return data.mapIndexed { index, databaseData ->
            DisplayDataType(index, "${databaseData.foo} ${databaseData.bar}")
        }
    }
}
val adapter = DataDisplayAdapter(DataDisplay())
val data = DatabaseDataGenerator().generateData()
val output = adapter.convertData(data)

// 2. facade (provides a simple interface to complex functionality)
// it's a simple class like a repository, that holds a private field and exposes methods to act on it
// Repository pattern that wraps network + database is a facade. 

// 3. decorator (attach new behavior to an existing object, or override existing behavior)
// example:  OkHttp interceptors, Retrofit call adapters.
interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}
class StandardCoffeeMachine: CoffeeMachine {
    override fun makeSmallCoffee() = println("Making small coffee")
    override fun makeLargeCoffee() = println("Making large coffee")
}
// "by" means use the functionality from the class that was passed
class EnhancedCoffeeMachine(val coffeeMachine: CoffeeMachine): CoffeeMachine by coffeeMachine {
    // overriding behavior
    override fun makeLargeCoffee() = println("Enhanced: Making extra large coffee")
    fun makeCoffeeWithMilk() = println("Enhanced: Making coffee with milk")
}
val machine = EnhancedCoffeeMachine(StandardCoffeeMachine())

// 4. proxy (provide some functionality before and/or after calling an object)
// similar to decorator, except it manages the lifecycle of its object
// examples: Lazy initialization, permission handling, Room @Query under the hood
interface Image {
    fun display()
}
class RealImage(private val filename: String): Image {
    init {
        loadFromDisk(filename)
    }
    override fun display() {
        println("RealImage: Displaying $filename")
    }
    private fun loadFromDisk(filename: String) {
        println("RealImage: Loading $filename")
    }
}
class ProxyImage(private val filename: String): Image {
    private var realImage: RealImage? = null
    override fun display() {
        if (realImage == null) {
            realImage = RealImage(filename)
        }
        realImage!!.display()
    }
}
val image = ProxyImage("test.jpg")
image.display()
image.display() // this is cached so Loading is no longer printed
