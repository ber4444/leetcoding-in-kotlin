package Coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// "Write a debounce function using coroutines"

/**
 * Debounce function that delays execution until a specified time has passed
 * without new calls. Useful for search input, resize events, etc.
 */
class Debouncer<T>(
    private val delayMillis: Long = 300,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    private var job: Job? = null

    @Suppress("unused")
    fun debounce(action: suspend () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            action()
        }
    }

    fun debounceWithValue(value: T, action: suspend (T) -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(delayMillis)
            action(value)
        }
    }

    @Suppress("unused")
    suspend fun cancel() {
        job?.cancelAndJoin()
    }
}

// Alternative: Functional approach using extension function
fun <T> CoroutineScope.debounce(
    delayMillis: Long = 300,
    action: suspend (T) -> Unit
): (T) -> Unit {
    val debouncer = Debouncer<T>(delayMillis, this)
    return { value: T -> debouncer.debounceWithValue(value, action) }
}

// Demo 1: Debouncing a search query
runBlocking {
    println("--- Demo 1: Search Debounce ---")
    val debouncer = Debouncer<String>(300)
    var searchCount = 0

    // Simulate rapid search input
    repeat(5) { index ->
        debouncer.debounceWithValue("query$index") { query ->
            searchCount++
            println("Performing search for: $query (attempt $searchCount)")
        }
        delay(100) // User types quickly
    }

    delay(500) // Wait for final debounce to complete
    println("Total searches performed: $searchCount\n")
}

// Demo 2: Debouncing window resize events
runBlocking {
    println("--- Demo 2: Window Resize Debounce ---")
    val debouncer = Debouncer<Pair<Int, Int>>(200)
    var resizeCount = 0

    // Simulate rapid resize events
    listOf(800 to 600, 810 to 600, 820 to 610, 830 to 620).forEach { size ->
        debouncer.debounceWithValue(size) { (width, height) ->
            resizeCount++
            println("Resizing window to: $width x $height (attempt $resizeCount)")
        }
        delay(50) // Resize events happen rapidly
    }

    delay(300) // Wait for final debounce to complete
    println("Total resize operations: $resizeCount\n")
}

// Demo 3: Using the functional approach
runBlocking {
    println("--- Demo 3: Functional Debounce ---")
    val scope = CoroutineScope(Dispatchers.Default)
    var updateCount = 0

    val debouncedUpdate = scope.debounce<String>(250) { value ->
        updateCount++
        println("Updated with value: $value (attempt $updateCount)")
    }

    // Simulate rapid updates
    repeat(4) { index ->
        debouncedUpdate("value$index")
        delay(80)
    }

    delay(400) // Wait for debounce to complete
    println("Total updates performed: $updateCount")
}









