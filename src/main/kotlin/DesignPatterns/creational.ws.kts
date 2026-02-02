// 1. singleton (uses: network manager, DB access, logging, utility classes)
// scoped singletons with DI are preferable, generally
object SomeClass

// 2. factory (provides way to access functionality without caring about implementation)
// good for separation of concerns, testability
// e.g. here the currency impl details are in the factory
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

// 3. abstract factory (when we don't care about where we get the object or how we get it)
// one level of abstraction over the factory pattern
interface DataSource
class DatabaseDataSource : DataSource
class NetworkDataSource : DataSource
abstract class DataSourceFactory {
    abstract fun makeDataSource(): DataSource
    companion object {
        inline fun <reified T : DataSource> createFactory(): DataSourceFactory =
            when (T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}
class DatabaseFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}
class NetworkFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}
val dataSource = DataSourceFactory.createFactory<DatabaseDataSource>().makeDataSource()
println(dataSource is DatabaseDataSource)

// 4. builder (used when we have multiple params and some are optional)
// this is solved with named parameters

// 5. lazy initialization (initialize a resource when it's needed, not when declared)
val source by lazy { Object() }
// or with "var":
class Foo {
    lateinit var bar: Object
}

// 6. prototype (lets you copy existing objects without depending on their concrete classes)
abstract class Shape: Cloneable {
    open val type: String? = null
    public override fun clone(): Any {
        return super.clone()
    }
}
class Rectangle(): Shape() {
    override val type: String = "rectangle"
}
class Circle(): Shape() {
    override val type: String = "circle"
}
val map: Map<String?, Shape> = mapOf("shape1" to Rectangle(), "shape2" to Circle())
val shape = map["shape1"]?.clone() as Shape
println(shape.type == map["shape1"]?.type)
