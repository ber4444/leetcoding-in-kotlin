import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 1. observer (define a subscription mechanism) ; could use LiveData but that is not KMP compatible
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

// 2. Strategy - algorithm switching (e.g., different caching strategies)

// 3. State - managing UI states in complex flows

// 4. Command - for undo/redo or task queuing scenarios

// focus on Android-specific use cases - WorkManager strategies, navigation states

// other aren't relevant for mobile
