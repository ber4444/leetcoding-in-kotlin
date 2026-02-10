package Coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

val scope = CoroutineScope(Dispatchers.Main.immediate) // main thread
scope.launch {
    val ret = heavyProcessing()
    println("Heavy processing result: $ret") // update ui on main thread with `ret`
}
suspend fun heavyProcessing(): Int {
    return withContext(Dispatchers.Default) { // execute on bg thread
        // do something CPU intense here...
        0
    }
}

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

        CombinedResult(user.await(), posts.await())
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

