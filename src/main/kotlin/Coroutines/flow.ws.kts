package Coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.http.HttpResponse
import kotlin.coroutines.EmptyCoroutineContext

// flows are asynchronous data streams, e.g. for listening to changes in a database
// (as opposed to kotlin's sequences which are synchronous data streams)

fun simple(): Flow<Int> = flow { // flow builder declaration; no code is executed
    for (i in 1..3) {
        delay(100) // you could make a network call here (via a suspend fun method)
        emit(i) // emit next value
    }
}
    .buffer() // optional - it introduces concurrency between the producer and the consumer
              // the buffer size is 64 by default (configurable, also what happens when the
    // producer is faster than the consumer is also configurable in the onBufferOverflow)
    .flowOn(Dispatchers.Default)

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
    // we also have last(), single(), toSet(), toList(), fold(), reduce(), collectLatest, mapLatest
}

// or:

runBlocking {
    (1..3).asFlow() // or use flowOf(1, 2, 3).cancellable()
        // intermediate operators you can use here include transform, filter, etc. see flowmarbles.com
        .map { request -> performRequest(request) }
        //onStart{}.onEach{}.onCompletion{}.catch{}.retry{}
        .collect { response -> println(response) }
}
fun performRequest(request: Int): String {
    return "response $request"
}

// launching and collecting without suspending the coroutine in which it is called
// (unlike when using collect, which will suspend)
runBlocking {
    simple().launchIn(CoroutineScope(EmptyCoroutineContext))
}
/* android notes:
 flow.launchIn(viewModelScope)
 // but do not use LiveData in KMP, as it is Android specific, use instead State (or the complex StateFlow)
 val uiState: LiveData<UiState> = dataSourceFlow
    .map { UiState.Success(it) }
    .onStart { emit(UiState.Loading) }
    .asLiveData()
 */

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
fun networkFlowWithRetryOperator(): Flow<String> = flow {
    emit(fetchDataWithFailure(0))
}.retry(retries = 2) { cause ->
    println("Retrying due to: ${cause.message}")
    delay(100L) // wait before retry
    true // return true to retry, false to stop
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
