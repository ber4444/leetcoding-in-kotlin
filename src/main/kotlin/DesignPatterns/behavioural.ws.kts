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
    delay(100)
    eventManager.triggerEvent("Hello, StateFlow!")
    delay(200)
}