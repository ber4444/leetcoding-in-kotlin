package Threads

import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
// I tried to add the dependency via gradle but something is broken

val atomicInteger = AtomicInteger(0)

runBlocking {
    repeat(10) { launch { add() }}
}
suspend fun add() = atomicInteger.incrementAndGet()
println(atomicInteger)


///// using flow:

fun simple(): Flow<Int> = flow { // flow builder
    for (i in 1..3) {
        delay(100) // you could make a network call here (via a suspend fun method)
        emit(i) // emit next value
    }
}

fun main() = runBlocking<Unit> {
    // Launch a concurrent coroutine to check if the main thread is blocked
    launch {
        for (k in 1..3) {
            println("I'm not blocked $k")
            delay(100)
        }
    }
    // Collect the flow (the flow won't run until it's collected)
    simple().collect { value -> println(value) } 
}

// or:

runBlocking<Unit> {
    (1..3).asFlow() // a flow of requests
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }
}
fun performRequest(request: Int): String {
    return "response $request"
}
// see more at https://kotlinlang.org/docs/flow.html#declarative-handling
// and https://developer.android.com/kotlin/flow
