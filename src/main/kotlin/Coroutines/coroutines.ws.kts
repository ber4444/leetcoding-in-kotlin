package Coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

// coroutines are very lightweight, run on existing threads and pause/resume without blocking threads (unless you use the runBlocking coroutine builder)
// coroutines enable async, non-blocking execution with sequential-looking code
// suspend functions are special functions that can suspend coroutines, act like AI checkpointing
// you can turn callback-based APIs to suspend functions with suspendCancellableCoroutine, in which you call continuation.resume() or continuation.resumeWithException()

// Dispatchers.Default is backed by a thread pool with a size equal to your CPU core count (at least 2)
// It's good for pure math, image processing, or heavy data crunching.

// structured concurrency means every coroutine has a clear lifecycle tied to its scope
// parent coroutine waits for all child coroutines to complete before it finishes; canceling parent cancels children unless you use a supervisorScope{} for them
// or in the root coroutine, use CoroutineScope(SupervisorJob() + handler) where handler is a CoroutineExceptionandler
// if a child fails, the failure can propagate to the parent, optionally canceling it with all children
runBlocking {
	val job1 = launch { println("Task 1") }
	val job2 = launch { println("Task 2") }
	job1.join()
	job2.join()
	println("Tasks completed")
}

// val scope = CoroutineScope(Dispatchers.Main.immediate) // Dispatchers.Main requires UI environment (Android/Swing)
val scope = CoroutineScope(Dispatchers.Default) // run on background thread
// other dispatchers: Dispatchers.IO, Dispatchers.Default (for CPU intensive tasks)
// use ViewModelScope on Android and in CMP
scope.launch { // creates a new coroutine; if you want a Deferred result, use async{} instead
    withContext(Dispatchers.IO) { // move from default thread to IO thread within coroutine
        val ret = heavyProcessing()
        // withContext(Dispatchers.Main) { ... } // move back to main thread (requires UI environment)
        println("Heavy processing result: $ret")
    }
}
// it also can take an exception handler
// that defines how uncaught exceptions should be handled - make sure you do it in the root coroutine, not in a child coroutine
@Suppress("RedundantSuspendModifier")
suspend fun heavyProcessing() = 0

// use job.cancelAndJoin() to cancel + wait for completion safely; cancel does not immediately stop the coroutine, cancellation is "cooperative" - it will end at the first suspention point such as await(), yield(), delay(), etc
// you can add an ensureActive() call to the suspending method as a suspention point
// at the first suspention point, a CancellationException will be thrown, and it's recommended to try-catch that, do cleanup and then re-throw it to propagate to the parent
// note that suspending work needs to run in a withContext(NonCancellable) during cleanup
// or use job.invokeOnCompletion() for cleanup, this will be called when the coroutine is cancelled (the exception here will have a CancellationException in that case)

// "Implement a function that fetches data from two APIs in parallel and combines results"

data class User(val id: Int, val name: String)
data class Posts(val userId: Int, val postCount: Int)
data class CombinedResult(val user: User, val posts: Posts)

// Simulated API calls
suspend fun fetchUser(userId: Int): User {
    return withContext(Dispatchers.Default) {
        // Simulate network delay
        kotlinx.coroutines.delay(100)
        User(userId, "User $userId")
    }
}

suspend fun fetchPosts(userId: Int): Posts {
    return withContext(Dispatchers.Default) {
        // Simulate network delay
        kotlinx.coroutines.delay(150)
        Posts(userId, postCount = 5)
    }
}

// Fetch from two APIs in parallel and combine results
suspend fun fetchUserWithPosts(userId: Int): CombinedResult {
    return withContext(Dispatchers.IO) {
        val user = async { fetchUser(userId) }
        val posts = async { fetchPosts(userId) }

        // suspend the callers until the coroutines finish
        CombinedResult(user.await(), posts.await()) // use join instead of await if you started the coroutine with launch (and/or use a parent coroutine scope, which automatically wait for each child)
    }
}

// Alternative: using awaitAll for multiple parallel calls
suspend fun fetchMultipleUsersWithPosts(userIds: List<Int>): List<CombinedResult> {
    return withContext(Dispatchers.IO) {
        userIds.map { userId ->
            async { fetchUserWithPosts(userId) }
        }.awaitAll()
    }
}

// Demo
runBlocking {
    println("Fetching user with posts in parallel...")
    val result = fetchUserWithPosts(1)
    println("Result: $result")
}
