package DesignPatterns

// 1. Observer (define a subscription mechanism)
// Android Interview Hint: This is the backbone of Reactive Programming.
// - Usage: LiveData, Kotlin Flow, RxJava, and listeners (OnClickListener).
// - System Design: Real-time apps (Chat, Stock Tickers) where the UI must react to data changes immediately.
// see Coroutines folder; it shows reactive UI updates with Flow

// 2. Strategy - algorithm switching
// Android Interview Hint: Use this to make components interchangeable and testable.
// - Usage: 
//   - RecyclerView.LayoutManager (LinearLayoutManager vs GridLayoutManager).
//   - Network Retry Policies (ExponentialBackoff vs Linear).
//   - Image Loading (DiskCache vs MemoryCache strategies).
//   - Authentication (GoogleAuth vs FacebookAuth vs EmailAuth implementations).
class Printer(var stringFormatterStrategy: (String) -> String) {
    fun printString(message: String): String {
        return stringFormatterStrategy(message)
    }
}

val lowerCaseFormatter: (String) -> String = { it.lowercase() }
val upperCaseFormatter: (String) -> String = { it.uppercase() }

val printer = Printer(lowerCaseFormatter)
println("Lowercase Strategy: " + printer.printString("Hello, World!"))

// Switching strategy at runtime
printer.stringFormatterStrategy = upperCaseFormatter
println("Uppercase Strategy: " + printer.printString("Hello, World!"))


// 3. State - managing UI states in complex flows, using a finite state machine
// Android Interview Hint: Essential for robust UI/UX, avoiding "illegal" states.
// - Usage: 
//   - Media Player (Playing, Paused, Buffering, Error).
//   - Network Connection (Connected, Connecting, Disconnected).
//   - MVI (Model-View-Intent) Architecture patterns (LCE: Loading, Content, Error).
sealed class AuthorizationState {
    object Unauthorized : AuthorizationState()
    class Authorized(val username: String) : AuthorizationState()
}

class AuthorizationManager {
    private var state: AuthorizationState = AuthorizationState.Unauthorized

    fun login(username: String) {
        state = AuthorizationState.Authorized(username)
        println("User logged in: $username")
    }

    fun logout() {
        state = AuthorizationState.Unauthorized
        println("User logged out")
    }

    fun getCurrentUser(): String {
        return when (val s = state) {
            is AuthorizationState.Authorized -> s.username
            is AuthorizationState.Unauthorized -> "Unknown"
        }
    }
}

val authManager = AuthorizationManager()
println("User: ${authManager.getCurrentUser()}") // Unknown
authManager.login("Alice")
println("User: ${authManager.getCurrentUser()}") // Alice
authManager.logout()


// 4. Command - for undo/redo or task queuing scenarios
// Android Interview Hint: Decouples the invoker from the receiver.
// - Usage: 
//   - "Undo" functionality in text editors or canvas drawing apps.
//   - WorkManager (enqueueing deferrable background tasks).
//   - Event Bus implementations.
interface Command {
    fun execute()
}

class OrderAddCommand(val id: Long) : Command {
    override fun execute() {
        println("Adding order with id: $id")
    }
}

class OrderPayCommand(val id: Long) : Command {
    override fun execute() {
        println("Paying for order with id: $id")
    }
}

class CommandProcessor {
    private val queue = arrayListOf<Command>()
    
    fun addToQueue(command: Command): CommandProcessor = apply {
        queue.add(command)
    }
    
    fun processCommands(): CommandProcessor = apply {
        queue.forEach { it.execute() }
        queue.clear()
    }
}

CommandProcessor()
    .addToQueue(OrderAddCommand(123L))
    .addToQueue(OrderPayCommand(456L))
    .processCommands()
