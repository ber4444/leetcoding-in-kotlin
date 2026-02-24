package DesignPatterns

// 1. singleton (uses: network manager, DB access, logging, utility classes)
// scoped singletons with DI are preferable, generally
// examples: Retrofit instances, Room databases, repository classes.
// Kotlin object is inherently thread-safe and lazy-initialized (created only when first accessed)
// System Design: Essential for managers like BluetoothManager, AudioFocusManager.
// Be wary of testing; singletons hold global state. In Android, the Application class is a natural singleton.
// Dependency Injection (Hilt/Koin) allows scoping (SingletonComponent) which is better than static singletons.
object SomeClass
// for singletons needing custom initialization logic, use double-checked locking, which requires explicit use of @Volatile and synchronized blocks

// 2. factory (provides way to access functionality without caring about implementation)
// good for separation of concerns, testability
// factory lets us defer an object's instantiation to a subclass, as opposed to calling the constructor directly
// example: ViewModel factories
// e.g. here the currency impl details are in the factory
// System Design: RecyclerView Adapters often use a factory approach for creating ViewHolders based on viewType.
// Dependency Injection libraries generate factories for your classes.
// FragmentFactory allows passing dependencies to Fragment constructors.
sealed class Country {
    object USA : Country()
    class Canada : Country()
    data class UK(val bar: String) : Country()
}
class Currency(val code: String)
object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency =
        when (country) {
            Country.USA -> Currency("USD")
            is Country.Canada -> Currency("CAD")
            is Country.UK -> Currency("GBP")
        }
}
val currency = CurrencyFactory.currencyForCountry(Country.USA)
println(currency.code)

// 3. builder (used when we have multiple params and some are optional)
// this is solved with named parameters
// examples: AlertDialog.Builder, Notification.Builder, Retrofit configuration
// builder solves problem  when many variants of constructor are created with increasing number of arguments
// System Design: Constructing complex objects like an HTTP Request in OkHttp or ImageRequest in Coil.
// Useful when an object is immutable once created but has many optional configuration steps.

// 4. lazy initialization (initialize a resource when it's needed, not when declared)
// System Design: Critical for App Startup performance. Don't initialize heavy components (like payment SDKs, heavy DBs) in Application.onCreate unless necessary.
// Android KTX provides delegates like 'by viewModels()' which lazily create ViewModels.
val source by lazy { Object() }
// or with "var":
class Foo {
    lateinit var bar: Object
}

// 5. prototype (lets you copy existing objects without depending on their concrete classes)
// System Design: In Kotlin, data classes come with a 'copy()' method which implements this pattern.
// Useful for Immutable State reducers (MVI architecture) where you copy the previous state and change one field.
// Also used in Optimistic UI updates to create a temporary state copy.
abstract class Shape: Cloneable {
    open val type: String? = null
    public override fun clone(): Any {
        return super.clone()
    }
}
class Rectangle : Shape() {
    override val type: String = "rectangle"
}
class Circle : Shape() {
    override val type: String = "circle"
}
val map: Map<String?, Shape> = mapOf("shape1" to Rectangle(), "shape2" to Circle())
val shape = map["shape1"]?.clone() as Shape
println(shape.type == map["shape1"]?.type)