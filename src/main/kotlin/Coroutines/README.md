# Kotlin Coroutines Interview Preparation Guide

A concise Q&A reference for Kotlin coroutines interview questions, organized by difficulty level.

---

## 🟢 Junior/Intermediate Level

### 1. What are coroutines and how do they differ from threads?

**Coroutines:**
- Lightweight concurrency primitives that allow you to write asynchronous code in a sequential style
- Much cheaper than threads (can create thousands or millions without significant overhead)
- Are suspended (not blocked) when waiting, freeing up the underlying thread
- Cooperative multitasking - coroutines voluntarily yield control at suspension points

**Threads:**
- OS-level constructs with significant overhead (~1MB stack per thread)
- Limited by system resources (typically hundreds, not thousands)
- Blocking operations hold the entire thread
- Preemptive multitasking - OS decides when to switch threads

**Key difference:** Coroutines suspend; threads block. A single thread can run many coroutines.

```kotlin
// Thread - blocking
thread {
    Thread.sleep(1000) // Thread is blocked
    println("Done")
}

// Coroutine - suspending
launch {
    delay(1000) // Coroutine suspends, thread is free
    println("Done")
}
```

---

### 2. Explain suspend functions

**Definition:** Functions that can be paused and resumed later without blocking the thread.

**Key Points:**
- Marked with `suspend` keyword
- Can only be called from other suspend functions or coroutines
- Compiler transforms them into state machines using continuations
- Don't block the underlying thread when waiting

```kotlin
suspend fun fetchUserData(userId: String): User {
    delay(1000) // Suspends coroutine, doesn't block thread
    return api.getUser(userId) // Another suspend function
}

// Usage
launch {
    val user = fetchUserData("123") // Suspends here until data arrives
    updateUI(user)
}
```

**Interview tip:** "A suspend function is like a regular function, but it can be paused at suspension points (marked by other suspend function calls) and resumed later."

---

### 3. launch vs async - when to use each?

**launch:**
- Fire-and-forget: starts a coroutine without returning a result
- Returns a `Job` for lifecycle management
- Exceptions are propagated to parent scope immediately
- Use when: you don't need a return value (side effects, UI updates, logging)

**async:**
- Returns a `Deferred<T>` which is a future/promise
- Call `.await()` to get the result
- Exceptions are held until you call `await()`
- Use when: you need to compute and return a value

```kotlin
// launch - no return value needed
launch {
    saveToDatabase(data)
    println("Saved!")
}

// async - need the result
val deferred = async {
    fetchFromNetwork()
}
val result = deferred.await() // Get result here
```

**Parallel execution with async:**
```kotlin
val user = async { fetchUser() }
val posts = async { fetchPosts() }
// Both run in parallel
val userData = user.await()
val postData = posts.await()
```

---

### 4. What's the difference between lifecycleScope and viewModelScope?

**lifecycleScope:**
- Tied to a Composable's lifecycle (via `LocalLifecycleOwner`)
- Automatically cancelled when the lifecycle owner is destroyed
- Cancelled when the Composable leaves the composition permanently
- Use for: UI-related operations that should stop when UI is destroyed

**viewModelScope:**
- Tied to a ViewModel's lifecycle
- Automatically cancelled when ViewModel is cleared
- Survives configuration changes (screen rotation)
- Use for: business logic, data operations that should survive config changes

```kotlin
// In Composable (via LaunchedEffect or rememberCoroutineScope)
@Composable
fun MyScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current
    
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycleScope.launch {
            // Cancelled when Composable leaves composition
            collectUIState()
        }
    }
}

// In ViewModel
viewModelScope.launch {
    // Cancelled when ViewModel cleared
    // Survives recomposition
    loadData()
}
```

**Interview tip:** "Use viewModelScope for data operations that should survive recomposition, lifecycleScope for UI-bound operations in Composables."

---

### 5. Dispatchers: IO vs Default vs Main

**Dispatchers.Main:**
- Runs on Android's main/UI thread
- Use for: UI updates, quick operations
- Limited to main thread only

**Dispatchers.IO:**
- Optimized for I/O operations (network, disk, database)
- Backed by a shared pool of threads (max 64 or # of cores, whichever is larger)
- Use for: reading/writing files, network calls, database queries

**Dispatchers.Default:**
- Optimized for CPU-intensive work
- Thread pool size = # of CPU cores (minimum 2)
- Use for: sorting large lists, JSON parsing, complex calculations, data processing

```kotlin
launch(Dispatchers.Main) {
    progressBar.show()
    
    val data = withContext(Dispatchers.IO) {
        database.fetchData() // Switches to IO thread
    }
    
    val processed = withContext(Dispatchers.Default) {
        heavyProcessing(data) // Switches to CPU thread
    }
    
    updateUI(processed) // Back on Main
}
```

**Interview tip:** "Main for UI, IO for blocking I/O, Default for CPU work."

---

### 6. Structured concurrency and cancellation

**Structured Concurrency:**
- Parent-child relationship between coroutines
- Parent waits for all children to complete
- Parent cancellation cascades to all children
- Scope defines lifecycle and error handling boundaries

**Benefits:**
- No leaked coroutines
- Automatic cleanup
- Clear lifecycle management
- Predictable error propagation

```kotlin
// Parent scope
launch {
    // Child 1
    launch { doTask1() }
    // Child 2
    launch { doTask2() }
    // Parent waits for both children
}

// Cancellation
val job = launch {
    repeat(1000) { i ->
        if (!isActive) return@launch // Check cancellation
        delay(100)
    }
}
job.cancel() // Cancels the coroutine
```

**Cancellation best practices:**
- Always check `isActive` in loops
- Use `ensureActive()` to throw if cancelled
- Clean up resources in `finally` blocks
- Use `withTimeout` for automatic cancellation

```kotlin
launch {
    try {
        withTimeout(5000) {
            longRunningOperation()
        }
    } catch (e: TimeoutCancellationException) {
        println("Timed out!")
    }
}
```

---

## 🟡 Senior/Principal Level

### 7. StateFlow vs SharedFlow - when to use which?

**StateFlow:**
- Hot flow that always has a value (state holder)
- Conflates values (only latest matters)
- New collectors immediately get current value
- Requires initial value
- Use for: UI state, configuration, single source of truth

**SharedFlow:**
- Hot flow that can have zero or multiple values
- Configurable replay cache and buffer
- No initial value required
- Can emit without collectors (broadcasts)
- Use for: events, notifications, one-time signals

```kotlin
// StateFlow - state holder
class ViewModel {
    private val _uiState = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    fun updateState() {
        _uiState.value = UiState.Success(data)
    }
}

// SharedFlow - events
class ViewModel {
    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events.asSharedFlow()
    
    fun showToast() {
        _events.emit(Event.ShowToast("Hello"))
    }
}
```

**Configuration:**
```kotlin
// SharedFlow with replay
MutableSharedFlow<String>(
    replay = 1,        // Cache last 1 value
    extraBufferCapacity = 64,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)
```

**Interview tip:** "StateFlow for state (latest value matters), SharedFlow for events (all emissions matter)."

---

### 8. Error handling with CoroutineExceptionHandler

**CoroutineExceptionHandler:**
- Catches uncaught exceptions in coroutines
- Last resort error handler
- Only works with launch (not async)
- Must be installed in root coroutine scope

```kotlin
val handler = CoroutineExceptionHandler { _, exception ->
    println("Caught: ${exception.message}")
    logToAnalytics(exception)
}

// Install in scope
val scope = CoroutineScope(Dispatchers.Main + handler)

scope.launch {
    throw RuntimeException("Oops!") // Caught by handler
}
```

**Structured error handling:**
```kotlin
launch {
    try {
        val result = async { riskyOperation() }.await()
    } catch (e: Exception) {
        handleError(e)
    }
}

// SupervisorJob - children failures don't affect siblings
val supervisor = SupervisorJob()
CoroutineScope(Dispatchers.Main + supervisor).launch {
    launch { mayFail1() } // Failure doesn't cancel sibling
    launch { mayFail2() }
}
```

**Best practices:**
- Use try-catch for expected errors
- Use CoroutineExceptionHandler for unexpected errors
- Consider SupervisorJob when children should fail independently
- async exceptions require await() or they're lost

---

### 9. How to combine multiple coroutine results?

**Parallel execution with async/await:**
```kotlin
suspend fun loadDashboard(): Dashboard = coroutineScope {
    val user = async { fetchUser() }
    val posts = async { fetchPosts() }
    val comments = async { fetchComments() }
    
    Dashboard(
        user = user.await(),
        posts = posts.await(),
        comments = comments.await()
    )
}
```

**Using combine for Flows:**
```kotlin
val combined = combine(
    userFlow,
    postsFlow,
    commentsFlow
) { user, posts, comments ->
    Dashboard(user, posts, comments)
}
```

**Zip for Flows (waits for both):**
```kotlin
flow1.zip(flow2) { a, b ->
    a to b
}.collect { (first, second) ->
    println("$first, $second")
}
```

**Race (first to complete):**
```kotlin
select<String> {
    async { fetchFromCache() }.onAwait { it }
    async { fetchFromNetwork() }.onAwait { it }
}
```

**Interview tip:** "Use async/await for independent parallel operations, combine for continuous Flow streams, select for racing operations."

---

### 10. Testing coroutines with runTest and TestDispatcher

**runTest:**
- Replaces runBlocking for tests
- Auto-advances virtual time
- Skips delays instantly
- Catches uncompleted coroutines

```kotlin
@Test
fun testDataLoad() = runTest {
    val viewModel = MyViewModel()
    viewModel.loadData()
    
    // Delays are skipped, runs instantly
    assertEquals(expected, viewModel.data.value)
}
```

**TestDispatcher types:**
```kotlin
// StandardTestDispatcher - manual control
@Test
fun testWithManualControl() = runTest {
    val dispatcher = StandardTestDispatcher(testScheduler)
    
    launch(dispatcher) {
        delay(1000)
        println("Done")
    }
    
    advanceTimeBy(1000) // Manually advance time
    // Or advanceUntilIdle()
}

// UnconfinedTestDispatcher - immediate execution
@Test
fun testImmediate() = runTest {
    val dispatcher = UnconfinedTestDispatcher(testScheduler)
    // Executes immediately without delay
}
```

**Testing Flows:**
```kotlin
@Test
fun testFlow() = runTest {
    val flow = flowOf(1, 2, 3)
        .onEach { delay(1000) }
    
    val results = flow.toList() // Collects all, delays skipped
    assertEquals(listOf(1, 2, 3), results)
}

// Or use turbine library
@Test
fun testFlowWithTurbine() = runTest {
    myFlow.test {
        assertEquals(Item1, awaitItem())
        assertEquals(Item2, awaitItem())
        awaitComplete()
    }
}
```

**MainDispatcher for Android:**
```kotlin
@Before
fun setup() {
    Dispatchers.setMain(StandardTestDispatcher())
}

@After
fun tearDown() {
    Dispatchers.resetMain()
}
```

---

### 11. What are the various types of coroutine scopes?

**Built-in Scopes:**

1. **GlobalScope** (discouraged)
   - App-lifetime scope
   - No automatic cancellation
   - Risk of leaks
   ```kotlin
   GlobalScope.launch { } // DON'T USE
   ```

2. **CoroutineScope** (custom)
   - Create your own with Job + Dispatcher
   - Full control over lifecycle
   ```kotlin
   class MyClass {
       private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
       
       fun cleanup() {
           scope.cancel()
       }
   }
   ```

3. **lifecycleScope** (Jetpack Compose)
   - Composable lifecycle-bound
   - Auto-cancelled when Composable leaves composition

4. **viewModelScope** (Android)
   - ViewModel lifecycle-bound
   - Auto-cancelled when ViewModel cleared

5. **coroutineScope** (suspend function)
   - Creates a scope suspending current coroutine
   - Waits for all children
   - Propagates cancellation
   ```kotlin
   suspend fun loadAll() = coroutineScope {
       launch { task1() }
       launch { task2() }
       // Waits for both
   }
   ```

6. **supervisorScope** (suspend function)
   - Like coroutineScope but child failures don't cancel siblings
   ```kotlin
   supervisorScope {
       launch { mayFail() } // Doesn't affect sibling
       launch { alsoWork() }
   }
   ```

**Interview tip:** "Use framework-provided scopes (lifecycleScope, viewModelScope) when available. Create custom CoroutineScope for other use cases. Use coroutineScope/supervisorScope for structured concurrency in suspend functions."

---

### 12. How do you switch context in coroutines?

**withContext:**
- Switches dispatcher temporarily
- Suspends and resumes on original dispatcher
- Returns the result

```kotlin
suspend fun fetchAndProcess(): ProcessedData {
    // Starts on caller's dispatcher (e.g., Main)
    
    val rawData = withContext(Dispatchers.IO) {
        // Switches to IO thread
        database.fetch()
    } // Automatically switches back to Main
    
    val processed = withContext(Dispatchers.Default) {
        // Switches to CPU thread
        heavyProcessing(rawData)
    } // Automatically switches back to Main
    
    return processed
}

// Usage from Main
launch(Dispatchers.Main) {
    val result = fetchAndProcess() // Context switches handled internally
    updateUI(result) // Back on Main
}
```

**Launch with specific dispatcher:**
```kotlin
launch(Dispatchers.IO) {
    // Entire block runs on IO
}
```

**Dispatcher inheritance:**
```kotlin
launch(Dispatchers.Main) {
    // On Main
    launch {
        // Inherits Main dispatcher
    }
    launch(Dispatchers.IO) {
        // Explicitly uses IO
    }
}
```

**Interview tip:** "Use withContext for temporary context switches within suspend functions. It's more efficient than launching a new coroutine and automatically switches back."

---

### 13. Contrast Kotlin coroutines with Java threads

| Aspect | Kotlin Coroutines | Java Threads |
|--------|------------------|--------------|
| **Cost** | Very lightweight (~bytes) | Heavy (~1MB stack) |
| **Scalability** | Millions possible | Hundreds practical |
| **Blocking** | Suspend (non-blocking) | Block thread |
| **Cancellation** | Built-in cooperative | Manual, complex |
| **Exception Handling** | Structured | Try-catch everywhere |
| **Context Switching** | Cheap (in-process) | Expensive (OS-level) |
| **Code Style** | Sequential (suspend) | Callbacks/futures |
| **Thread Pool** | Managed automatically | Manual management |

**Code Comparison:**
```kotlin
// Java Threads
Thread thread = new Thread(() -> {
    try {
        Thread.sleep(1000);
        String result = fetchData();
        runOnUiThread(() -> updateUI(result));
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
});
thread.start();

// Kotlin Coroutines
launch(Dispatchers.Main) {
    delay(1000)
    val result = withContext(Dispatchers.IO) { fetchData() }
    updateUI(result)
}
```

**Interview tip:** "Coroutines are not a replacement for threads—they run on threads. They're a higher-level abstraction that makes concurrent programming easier, more readable, and more efficient."

---

### 14. Code snippet: Two coroutines in series vs parallel

**Sequential (Series) Execution:**
```kotlin
suspend fun executeSequentially(): Result = coroutineScope {
    println("Starting sequential execution")
    
    // Task 1 runs first
    val result1 = performTask1() // Takes 1000ms
    println("Task 1 completed: $result1")
    
    // Task 2 runs after Task 1 completes
    val result2 = performTask2() // Takes 1000ms
    println("Task 2 completed: $result2")
    
    // Total time: ~2000ms
    combineResults(result1, result2)
}

suspend fun performTask1(): String {
    delay(1000)
    return "Result1"
}

suspend fun performTask2(): String {
    delay(1000)
    return "Result2"
}
```

**Parallel Execution:**
```kotlin
suspend fun executeInParallel(): Result = coroutineScope {
    println("Starting parallel execution")
    
    // Both tasks start simultaneously
    val deferred1 = async { performTask1() } // Starts immediately
    val deferred2 = async { performTask2() } // Starts immediately
    
    // Wait for both to complete
    val result1 = deferred1.await()
    println("Task 1 completed: $result1")
    
    val result2 = deferred2.await()
    println("Task 2 completed: $result2")
    
    // Total time: ~1000ms (tasks ran in parallel)
    combineResults(result1, result2)
}
```

**Alternative parallel pattern:**
```kotlin
suspend fun executeInParallelAlt(): Result = coroutineScope {
    // Await both at once
    val (result1, result2) = awaitAll(
        async { performTask1() },
        async { performTask2() }
    )
    combineResults(result1, result2)
}
```

**Real-world example - API calls:**
```kotlin
// Sequential - 3 seconds total
suspend fun loadDashboardSequential() {
    val user = fetchUser()        // 1 sec
    val posts = fetchPosts()      // 1 sec
    val comments = fetchComments() // 1 sec
    updateUI(user, posts, comments)
}

// Parallel - 1 second total (all run simultaneously)
suspend fun loadDashboardParallel() = coroutineScope {
    val user = async { fetchUser() }
    val posts = async { fetchPosts() }
    val comments = async { fetchComments() }
    
    updateUI(
        user.await(),
        posts.await(),
        comments.await()
    )
}
```

**Key Takeaway:**
- **Series:** Use regular suspend function calls (one after another)
- **Parallel:** Use `async` + `await()` to run concurrently
- **Speedup:** If tasks are independent, parallel execution can reduce total time significantly

---

## 📚 Additional Topics

### Flow retry logic
```kotlin
flow {
    emit(fetchData())
}.retry(retries = 3) { cause ->
    (cause is IOException).also { shouldRetry ->
        if (shouldRetry) delay(1000) // Wait before retry
    }
}.catch { e ->
    emit(fallbackData())
}.collect { data ->
    updateUI(data)
}
```

### Debouncing user input
```kotlin
searchQueryFlow
    .debounce(300) // Wait 300ms after last emission
    .distinctUntilChanged() // Only if value changed
    .mapLatest { query ->
        searchAPI(query) // Cancels previous search if new query
    }
    .collect { results ->
        showResults(results)
    }
```

### Channel basics
```kotlin
val channel = Channel<Int>()

launch {
    for (x in 1..5) {
        channel.send(x) // Send to channel
    }
    channel.close()
}

launch {
    for (y in channel) { // Receive from channel
        println(y)
    }
}
```

---

## 💡 Interview Tips

1. **Start simple:** Begin with the basic definition, then elaborate with examples
2. **Use analogies:** "Coroutines are like lightweight threads" or "suspend functions pause execution"
3. **Show code:** Always have a code snippet ready for practical questions
4. **Mention trade-offs:** Discuss when NOT to use certain features
5. **Real-world context:** Relate to Android development (UI updates, API calls, database)
6. **Performance awareness:** Mention efficiency gains (memory, CPU)
7. **Error handling:** Always discuss exception handling when relevant
8. **Testing:** Show awareness of testability

---

## 🎯 Quick Reference

**When to use what:**
- `launch`: Fire-and-forget (no result needed)
- `async`: Parallel execution with results
- `withContext`: Switch dispatcher temporarily
- `Flow`: Streams of values over time
- `StateFlow`: UI state management
- `SharedFlow`: Event broadcasting
- `Channel`: Hot communication between coroutines
- `coroutineScope`: Structured concurrency in suspend functions
- `supervisorScope`: Independent child failure handling

**Common patterns:**
```kotlin
// Parallel API calls
val (a, b, c) = awaitAll(async { api1() }, async { api2() }, async { api3() })

// Timeout
withTimeout(5000) { longOperation() }

// Safe execution
runCatching { riskyOperation() }.getOrElse { fallback }

// Combine flows
combine(flow1, flow2) { a, b -> a + b }
```

---

*Last updated: 2026-02-09*
