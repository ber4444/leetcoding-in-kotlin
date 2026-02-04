package Threads

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

// flows are asynchronous data streams, e.g. for listening to changes in a database
// (as opposed to kotlin's sequences which are synchronous data streams)

fun simple(): Flow<Int> = flow { // flow builder declaration; no code is executed
    for (i in 1..3) {
        delay(100) // you could make a network call here (via a suspend fun method)
        emit(i) // emit next value
    }
}.flowOn(Dispatchers.Default)

runBlocking {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }
    // Collect the flow (the flow won't run until it's collected)
    simple().collect { value -> println(value) }
    // besides collect(), other terminal operators include first() which cancels the flow after the first emission
    // and first { it > 2 } which cancels the flow after the first emission that satisfies the condition
    // we also have last(), single(), toSet(), toList(), fold(), reduce()
}

// or:

runBlocking {
    (1..3).asFlow() // or use flowOf(1, 2, 3)
        // intermediate operators you can use here include transform, filter, etc. see flowmarbles.com
        .map { request -> performRequest(request) }
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
 flow.onStart{}.onEach{}.onCompletion{}.catch{}.launchIn(viewModelScope)
 val uiState: LiveData<UiState> = dataSourceFlow
    .map { UiState.Success(it) }
    .onStart { emit(UiState.Loading) }
    .asLiveData()
 */
