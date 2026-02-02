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

// 2. bridge
// e.g. different shapes and different colors linked, or devices and remote controls linked
// you can have as many implementations as you want without exponentially increasing
// the number of classes, e.g. RedCircle, RedRectangle, BlueCircle, BlueRectangle, ...
interface Device {
    var volume: Int
    fun getName(): String
}
class Radio: Device {
    override var volume = 0
    override fun getName() = "radio"
}
interface Remote {
    fun volumeUp()
    fun volumeDown()
}
class AdvancedRemote(val device: Device): Remote {
    override fun volumeUp() {
        device.volume++
    }

    override fun volumeDown() {
        device.volume--
    }
}
val foo = AdvancedRemote(Radio())

// 3. facade (provides a simple interface to complex functionality)
// it's a simple class like a repository, that holds a private field and exposes methods to act on it

// 4. decorator (attach new behavior to an existing object, or override existing behavior)
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

// 5. composite (combine multiple objects into one object, works when the core functionality
// can be represented as a tree; this will allow the tree to be manipulated as a whole)
open class Equipment(
    open val price: Int,
    val name: String
)
open class Composite(name: String): Equipment(0, name) {
    private val equipments = ArrayList<Equipment>()
    override val price: Int
        get() = equipments.sumOf { it.price }

    fun add(equipment: Equipment) = apply { equipments.add(equipment) }
}
class Computer: Composite("Mac")
class HardDrive: Equipment(250, "Hard Drive")
class Memory: Equipment(280, "Memory")
val mac = Computer()
    .add(HardDrive())
    .add(Memory())
println(mac.price)

// 6. proxy (provide some functionality before and/or after calling an object)
// similar to decorator, except it manages the lifecycle of its object
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