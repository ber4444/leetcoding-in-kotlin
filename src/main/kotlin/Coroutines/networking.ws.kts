import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

runBlocking {
    println("Requesting...")
    try {
        // in compose, this would be: val state by coinsListViewModel.state.collectAsStateWithLifecycle()
        VM().state.collect { value ->
            println(value.coins.joinToString("\n") { it.toString() })
            if (value.coins.isNotEmpty() || value.error != null) {
                // Exit collection after first real result
                return@collect
            }
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
        e.printStackTrace()
    }
}
// in compose, you'd get it as   //========presentation layer:
data class CoinsState( // we would annotate this @Stable in compose
    val error: String? = null,
    val coins: List<UiCoinListItem> = emptyList(),
)
data class UiCoinListItem(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val isPositive: Boolean,
)
class VM {
    private val _state = MutableStateFlow(CoinsState())
    val state = _state
        .onStart {
            getAllCoins()
        }.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Eagerly,
            initialValue = CoinsState()
        )
    private suspend fun getAllCoins() {
        // use case and data source would be injected in a real app
        when (val coinsResponse = GetCoinsListUseCase(KtorCoinsRemoteDataSource()).execute()) {
            is Res.Success -> {
                _state.update {
                    CoinsState(
                        coins = coinsResponse.data.map { coinItem ->
                            UiCoinListItem(
                                id = coinItem.coin.id,
                                name = coinItem.coin.name,
                                iconUrl = coinItem.coin.iconUrl,
                                symbol = coinItem.coin.symbol,
                                isPositive = coinItem.change >= 0,
                            )
                        }
                    )
                }
            }

            is Res.Err -> {
                _state.update {
                    it.copy(
                        coins = emptyList(),
                        error = coinsResponse.error.toString(),//we'd map this to a message in a real app
                    )
                }
            }
        }
    }
}
//========domain layer:
class GetCoinsListUseCase(private val client: KtorCoinsRemoteDataSource) {
    suspend fun execute(): Res<List<CoinModel>, DataError.Remote> {
        return client.getListOfCoins().map { dto ->
            dto.data.coins.map { it.toCoinModel() }
        }
    }
}
inline fun <D, E : DataError, R> Res<D, E>.map(transform: (D) -> R): Res<R, E> {
    return when (this) {
        is Res.Success -> Res.Success(transform(this.data))
        is Res.Err -> this
        else -> throw IllegalStateException("Unknown result type")
    }
}
//===============data layer (serves domain):
data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
)
data class CoinModel(
    val coin: Coin,
    val price: Double,
    val change: Double,
)
fun CoinItemDto.toCoinModel() = CoinModel(
    coin = Coin(
        id = uuid,
        name = name,
        symbol = symbol,
        iconUrl = iconUrl,
    ),
    price = price,
    change = change,
)
class KtorCoinsRemoteDataSource {
    companion object {
        private const val BASE_URL = "https://api.coinranking.com/v2"
        private val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                gson()
            }
        }
    }
    suspend fun getListOfCoins(): Res<CoinsResponseDto, DataError.Remote> {
        return safeCall {
            client.get("$BASE_URL/coins")
        }
    }
}
data class CoinsResponseDto(
    val data: CoinsListDto
)
data class CoinsListDto(
    val coins: List<CoinItemDto>
)
data class CoinItemDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: Double,
    val rank: Int,
    val change: Double,
)
sealed interface DataError {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }
}

typealias Result<D, E> = Res<D, E>

sealed interface Res<out D, out E : DataError> {
    data class Success<D>(val data: D) : Res<D, Nothing>
    data class Err<E : DataError>(val error: E) : Res<Nothing, E>
}

suspend inline fun <reified T> safeCall(
    execute: suspend () -> HttpResponse,
): Result<T, DataError.Remote> {
    return try {
        val response = execute()
        when (response.status.value) {
            in 200..299 -> {
                try {
                    Res.Success(response.body<T>())
                } catch (e: Exception) {
                    println("Deserialization error: ${e.message}")
                    Res.Err(DataError.Remote.SERIALIZATION)
                }
            }
            408 -> Res.Err(DataError.Remote.REQUEST_TIMEOUT)
            429 -> Res.Err(DataError.Remote.TOO_MANY_REQUESTS)
            in 500..599 -> Res.Err(DataError.Remote.SERVER)
            else -> {
                println("HTTP Error: ${response.status.value}")
                Res.Err(DataError.Remote.UNKNOWN)
            }
        }
    } catch(e: Exception) {
        println("Network error: ${e.message}")
        e.printStackTrace()
        Res.Err(DataError.Remote.UNKNOWN)
    }
}