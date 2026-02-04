import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 1. observer (define a subscription mechanism)
class EventManager {
    private val _event = MutableStateFlow<String?>(null)
    val event: StateFlow<String?> = _event

    fun triggerEvent(message: String) {
        _event.value = message
    }

    fun clearEvent() {
        _event.value = null
    }
}
runBlocking {
    val eventManager = EventManager()

    // Observe events
    launch {
        eventManager.event.collect { event ->
            event?.let {
                println("Received: $it")
                eventManager.clearEvent() // Mark as handled
            }
        }
    }

    // Emit events
    eventManager.triggerEvent("Hello, StateFlow!")
}

// 2. Chain of responsibility: for a request with multiple operations,
// we define a chain of handlers, each containing a reference to the next handler
// each handler can decide to process or not process the request, then pass it on or return result
// we can represent this chain as a tree
interface HandlerChain {
    fun addHeader(inputHeader: String): String
}
class AuthenticationHeader(val token: String?, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nAuthorization: $token"
            .let { next?.addHeader(it) ?: it }
}
class ContentTypeHeader(val contentType: String, var next: HandlerChain? = null): HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nContentType: $contentType"
            .let { next?.addHeader(it) ?: it }
}
val authHeader = AuthenticationHeader("12345")
val contentTypeHeader = ContentTypeHeader("json", authHeader)
authHeader.next = contentTypeHeader
println(authHeader.addHeader("Headers with authentication"))
