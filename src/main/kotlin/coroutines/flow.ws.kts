package coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// flows are asynchronous data streams, e.g. database as producer, viewmodel as collector
// (as opposed to kotlin's sequences which are synchronous data streams)
// Flows are already provided by DataStore, Room, Retrofit, WorkManager (former two are KMP)
// note, Ktor uses other primitives for HTTP streaming: https://ktor.io/docs/client-responses.html#streaming although it does support Flow for other things like SSE
/*
 Cold flow (default behavior) produces data only when it's actively collected; starts fresh for each collector - ideal for Repositories
 Hot flow continuously emits data regardless of collectors, e.g. MutableStateFlow - ideal for UI and ViewModel and for real-time data
 StateFlow replays the most recent value to new collectors. SharedFlow does not (by default) so that is useful for one-time ui events like snackbars, dialogs. Also SharedFlow can send to multiple collectors.
 Convert cold to hot using repo.fetchData().stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = RequestState.Loading)
 WhileSubscribed just makes sure that we start at the first collector and stop when all collectors leave
 5000 stands for 5 seconds, this avoids restarting the stream in a configuration change
 You can use the combine() operator to mix multiple flows into one UI state flow
 For both hot and cold (esp. when buffering is involved, e.g. flowOn does that) it's good practice to use the
 lifecycle aware coroutines from `lifecycle-runtime-ktx` and `lifecycle-runtime-compose` packages
 Pull from the `org.jetbrains.androidx.lifecycle:*` to ensure KMP compatibility
 In Activity.onCreate or Fragment.onViewCreated: lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.collect { ... update the ui here ... } } }
 --> starts coroutines whenever lifecycle goes beyond that state
 or viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {}
 In Compose: val uiState by viewModel.uiState.collectAsStateWithLifecycle() // get flow's latest value as Compose State and make sure no emissions are processed when the app goes to the background
 In ViewModel: init { viewModelScope.launch { dbFlow.collect { ... } } }
Explicit backing fields (Kotlin 2.0+ Experimental / Future):
val uiState: StateFlow<String> field = MutableStateFlow("") // you don't need to have a private val _uiState
 UI uses a StateFlow to observe and react to state changes from the ViewModel.
 Lifecycle:
    Created: Brief state after onCreate(); activity is not yet visible.
    Started: Activity is visible but not yet interactive (after onStart()).
    Resumed: Activity is visible and interactive.
    Paused: Activity is partially visible (e.g., a dialog appears).
    Stopped: Activity is completely hidden (background); onStop() is called.
    Destroyed: Activity is being removed from memory (onDestroy()).
 Flow respects cancellation - if the collector is cancelled (the code consuming the flow), the upstream
 emission stops.
 */

fun simple(): Flow<Int> = flow { // flow builder declaration; no code is executed
    for (i in 1..3) {
        delay(100) // you could make a network call here (via a suspend fun method)
        emit(i) // emit next value; or use emitAll(collection.asFlow())
    }
}
    .buffer() // optional - it introduces concurrency between the producer and the consumer
              // the buffer size is 64 by default (configurable, also what happens when the
    // producer is faster than the consumer is also configurable in the onBufferOverflow)
    .flowOn(Dispatchers.Default) // flowOn enables the emitter to run on a different dispatcher than the collector
// callbackFlow {} - adopts old, callback-based apis into flows
// channelFlow {} - for sending values from multiple coroutines to a channel in parallel
// snapshotFlow {} - converts Compose state reads (e.g. scroll state) to flow

runBlocking {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }
    // Collect the flow (the flow won't run until it's collected - it's "cold"; need to use a SharedFlow if you want "hot")
    simple().collect { value -> println(value) }
    // besides collect(), other terminal operators include first() which cancels the flow after the first emission
    // and first { it > 2 } which cancels the flow after the first emission that satisfies the condition
    // we also have last(), single(), toSet(), toList(), fold(), reduce(), collectLatest, mapLatest, launchIn, filter, distinctUntilChanged, take
    // flatMapLatest() is useful for a Search-as-you-type functionality
}

// or:

runBlocking {
    (1..3).asFlow() // or use flowOf(1, 2, 3).cancellable()
        // intermediate operators you can use here include transform, filter, etc. see flowmarbles.com
        .map { request -> performRequest(request) } // or onEach
        //onStart{}  .onCompletion{}.catch{}.retry{} - used for error handling, call these before collect
        .collect { response -> println(response) }
}
fun performRequest(request: Int): String {
    return "response $request"
}

// launching and collecting without suspending the coroutine in which it is called
// (unlike when using collect, which will suspend)
runBlocking {
    simple().launchIn(this)
}

// Show how to handle network errors with retry logic using Flow

// Example of a network call that may fail
suspend fun fetchDataWithFailure(attempt: Int): String {
    delay(100L)
    if (attempt < 2) {
        throw Exception("Network error on attempt $attempt")
    }
    return "Successfully fetched data on attempt $attempt"
}

// Basic retry with exponential backoff
fun networkFlowWithBasicRetry(): Flow<String> = flow {
    var attempt = 0
    while (true) {
        try {
            val data = fetchDataWithFailure(attempt)
            emit(data)
            break
        } catch (e: Exception) {
            attempt++
            if (attempt >= 3) throw e
            delay(100L * (1 shl (attempt - 1))) // exponential backoff: 100ms, 200ms, 400ms
        }
    }
}

// Using built-in retry operator with condition
fun networkFlowWithRetryOperator(): Flow<String> {
    var attempt = 0
    return flow {
        emit(fetchDataWithFailure(attempt++))
    }.retry(retries = 2) { cause ->
        println("Retrying due to: ${cause.message}")
        delay(100L) // wait before retry
        true // return true to retry, false to stop
    }
}

// Advanced: retry with exponential backoff using custom logic
fun networkFlowWithExponentialBackoff(
    maxRetries: Long = 3,
    initialDelayMs: Long = 100,
    maxDelayMs: Long = 5000
): Flow<String> = flow {
    var attemptNumber = 0
    while (true) {
        try {
            emit(fetchDataWithFailure(attemptNumber))
            break
        } catch (e: Exception) {
            if (attemptNumber >= maxRetries - 1) throw e
            val delayMs = (initialDelayMs * (1 shl attemptNumber)).coerceAtMost(maxDelayMs)
            println("Attempt ${attemptNumber + 1} failed: ${e.message}. Retrying after ${delayMs}ms")
            delay(delayMs)
            attemptNumber++
        }
    }
}.catch { e ->
    println("Flow failed after all retries: ${e.message}")
    emit("Failed to fetch data")
}

// Demo: Retry with error handling and recovery
runBlocking {
    println("\n--- Demo: Network Flow with Retry ---")
    try {
        networkFlowWithRetryOperator().collect { data ->
            println("Received: $data")
        }
    } catch (e: Exception) {
        println("Final error: ${e.message}")
    }
}

// Demo: Retry with catch operator for graceful error handling
runBlocking {
    println("\n--- Demo: Network Flow with Catch ---")
    flow {
        repeat(3) { attempt ->
            try {
                emit(fetchDataWithFailure(attempt))
            } catch (e: Exception) {
                if (attempt < 2) {
                    delay(50L)
                    // Continue to next iteration for retry
                } else {
                    throw e
                }
            }
        }
    }.catch { e ->
        println("Error caught: ${e.message}")
        emit("Default fallback data")
    }.collect { data ->
        println("Received: $data")
    }
}
