# Kotlin Coroutines for Android - Interview Guide

## 1. How to Write Clean Async Code Using lifecycleScope/viewModelScope

### lifecycleScope
- **Lifecycle-aware coroutine scope** tied to Activity/Fragment lifecycle
- Automatically cancels when the lifecycle is destroyed
- Use for UI-related operations that should stop when the screen is gone

```kotlin
class MyFragment : Fragment() {
    fun loadData() {
        lifecycleScope.launch {
            // Automatically cancelled when fragment is destroyed
            val data = repository.fetchData()
            updateUI(data)
        }
    }
}
```

### viewModelScope
- **Tied to ViewModel lifecycle** - cancelled when ViewModel is cleared
- Survives configuration changes (rotation, etc.)
- Use for business logic that should persist across config changes

```kotlin
class MyViewModel : ViewModel() {
    fun loadUser() {
        viewModelScope.launch {
            // Survives rotation, cancelled when ViewModel is cleared
            _userState.value = repository.getUser()
        }
    }
}
```

### Best Practices
- **Never use GlobalScope** - it lives for the entire app lifecycle and can cause leaks
- **Use structured concurrency** - let scopes manage cancellation
- **Prefer viewModelScope** for data operations
- **Use lifecycleScope** for UI updates or view-specific work

---

## 2. Proper Dispatcher Selection for Different Operations

### Dispatchers.Main
- **Use for**: UI updates, calling suspend functions
- **Thread**: Main/UI thread only
- **Example**: Updating TextViews, showing dialogs

```kotlin
launch(Dispatchers.Main) {
    textView.text = "Updated"
    progressBar.visibility = View.GONE
}
```

### Dispatchers.IO
- **Use for**: Network requests, database operations, file I/O
- **Thread pool**: Shared pool optimized for I/O (up to 64 threads)
- **Example**: API calls, reading/writing files

```kotlin
withContext(Dispatchers.IO) {
    val response = apiService.getData()
    database.insert(response)
}
```

### Dispatchers.Default
- **Use for**: CPU-intensive work (sorting, parsing JSON, calculations)
- **Thread pool**: Limited to number of CPU cores
- **Example**: Heavy computations, data processing

```kotlin
withContext(Dispatchers.Default) {
    val sorted = largeList.sortedWith(complexComparator)
    processComplexData(sorted)
}
```

### Dispatchers.Unconfined
- **Rarely used** - not confined to any thread, can switch threads
- **Use for**: Testing only
- **Avoid in production** code

### Quick Reference
```kotlin
viewModelScope.launch {
    // Main by default
    showLoading()
    
    val data = withContext(Dispatchers.IO) {
        // Network/DB work
        api.fetchData()
    }
    
    val processed = withContext(Dispatchers.Default) {
        // CPU-intensive work
        processData(data)
    }
    
    // Back on Main
    updateUI(processed)
}
```

---

## 3. How to Avoid Blocking the UI Thread

### Problem: Blocking the Main Thread
```kotlin
// ❌ BAD - Blocks UI thread
fun loadData() {
    val data = repository.fetchDataBlocking() // Takes 2 seconds
    updateUI(data) // UI frozen for 2 seconds
}
```

### Solution 1: Use Suspend Functions
```kotlin
// ✅ GOOD - Non-blocking
fun loadData() {
    lifecycleScope.launch {
        val data = repository.fetchData() // suspend function
        updateUI(data) // UI stays responsive
    }
}
```

### Solution 2: Switch Dispatchers
```kotlin
// ✅ GOOD - Offload heavy work
lifecycleScope.launch {
    showLoading() // Main thread
    
    val result = withContext(Dispatchers.IO) {
        // Off main thread
        heavyOperation()
    }
    
    updateUI(result) // Back on main thread
}
```

### Solution 3: Use Flow for Streams
```kotlin
// ✅ GOOD - Reactive data stream
viewModelScope.launch {
    repository.dataFlow
        .flowOn(Dispatchers.IO) // Collect on IO thread
        .collect { data ->
            // Collected on Main thread
            updateUI(data)
        }
}
```

### Key Rules
1. **Never call blocking functions on Main** - use withContext to switch
2. **Make repository methods suspend** - let coroutines handle threading
3. **Use appropriate dispatchers** - IO for network/DB, Default for CPU work
4. **Keep UI updates on Main** - Android requires it
5. **Use Flow for continuous data** - better than LiveData for complex streams

---

## 4. Why Coroutines Are Better Than Callbacks/RxJava for Android

### vs. Callbacks

#### Callbacks (Callback Hell)
```kotlin
// ❌ Callback hell - hard to read and maintain
api.fetchUser(userId) { user ->
    api.fetchPosts(user.id) { posts ->
        api.fetchComments(posts[0].id) { comments ->
            // Nested callbacks are hard to follow
            updateUI(user, posts, comments)
        }
    }
}
```

#### Coroutines (Sequential Code)
```kotlin
// ✅ Coroutines - reads like synchronous code
lifecycleScope.launch {
    val user = api.fetchUser(userId)
    val posts = api.fetchPosts(user.id)
    val comments = api.fetchComments(posts[0].id)
    updateUI(user, posts, comments)
}
```

**Advantages over Callbacks:**
- **Linear, readable code** - no nesting
- **Better error handling** - try/catch works naturally
- **Automatic cancellation** - no manual cleanup
- **Less boilerplate** - no callback interfaces

### vs. RxJava

#### RxJava
```kotlin
// ❌ RxJava - steep learning curve, verbose
disposables += api.fetchUser(userId)
    .flatMap { user -> api.fetchPosts(user.id) }
    .flatMap { posts -> api.fetchComments(posts[0].id) }
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(
        { result -> updateUI(result) },
        { error -> handleError(error) }
    )
```

#### Coroutines
```kotlin
// ✅ Coroutines - simple and intuitive
viewModelScope.launch {
    try {
        val user = withContext(Dispatchers.IO) { api.fetchUser(userId) }
        val posts = withContext(Dispatchers.IO) { api.fetchPosts(user.id) }
        val comments = withContext(Dispatchers.IO) { api.fetchComments(posts[0].id) }
        updateUI(user, posts, comments)
    } catch (e: Exception) {
        handleError(e)
    }
}
```

**Advantages over RxJava:**
- **Simpler syntax** - less cognitive overhead
- **Smaller learning curve** - familiar imperative style
- **Better IDE support** - easier debugging
- **Lighter weight** - smaller library size
- **Native Kotlin** - first-class language support
- **Exception handling** - standard try/catch
- **Less boilerplate** - no dispose management

### Summary Table

| Feature | Callbacks | RxJava | Coroutines |
|---------|-----------|--------|------------|
| Readability | Poor (nested) | Medium | Excellent |
| Learning Curve | Low | High | Medium |
| Error Handling | Manual | Operators | try/catch |
| Cancellation | Manual | dispose() | Automatic |
| Android Integration | Basic | Good | Excellent |
| Library Size | N/A | Large | Small |

---

## 5. When to Use launch (Fire-and-Forget) vs async (Need Result)

### launch - Fire and Forget
**Use when**: You don't need a return value, just want to start concurrent work

```kotlin
// ✅ launch - no return value needed
viewModelScope.launch {
    logAnalytics(event)
    saveToDatabase(data)
    sendNotification()
}
```

**Characteristics:**
- Returns `Job` (not a value)
- Exceptions propagate immediately
- Use for side effects
- Independent operations

### async - Need Result
**Use when**: You need to get a result back from concurrent work

```kotlin
// ✅ async - parallel operations with results
viewModelScope.launch {
    val userDeferred = async { api.getUser() }
    val postsDeferred = async { api.getPosts() }
    
    // Wait for both (parallel execution)
    val user = userDeferred.await()
    val posts = postsDeferred.await()
    
    updateUI(user, posts)
}
```

**Characteristics:**
- Returns `Deferred<T>` (a result)
- Must call `.await()` to get value
- Exceptions thrown when you call `.await()`
- Use for parallel data fetching

### Common Patterns

#### Pattern 1: Parallel API Calls
```kotlin
// ✅ Fetch multiple APIs in parallel
suspend fun loadDashboard() = coroutineScope {
    val user = async { userApi.getUser() }
    val stats = async { statsApi.getStats() }
    val notifications = async { notificationApi.getAll() }
    
    Dashboard(
        user = user.await(),
        stats = stats.await(),
        notifications = notifications.await()
    )
}
```

#### Pattern 2: Fire-and-Forget Updates
```kotlin
// ✅ Independent background tasks
fun onUserAction() {
    viewModelScope.launch {
        launch { analytics.track("button_click") }
        launch { cache.update(data) }
        launch { api.incrementCounter() }
    }
}
```

#### Pattern 3: Sequential with Parallel Sections
```kotlin
// ✅ Mix of sequential and parallel
viewModelScope.launch {
    val userId = getCurrentUserId() // Sequential
    
    // Parallel section
    val profile = async { api.getProfile(userId) }
    val settings = async { api.getSettings(userId) }
    
    updateUI(profile.await(), settings.await())
    
    // Sequential again
    saveToCache(profile.await())
}
```

### When NOT to Use async
```kotlin
// ❌ Don't use async if you await immediately (no parallelism)
val result = async { api.getData() }.await() // Wasteful

// ✅ Just call it directly
val result = api.getData()
```

### Key Differences

| Feature | launch | async |
|---------|--------|-------|
| Return Type | Job | Deferred<T> |
| Get Result | N/A | .await() |
| Use Case | Side effects | Return value |
| Exception Handling | Immediate | On .await() |
| Parallelism | Yes (with multiple launches) | Yes (with multiple async) |

---

## 6. How Structured Concurrency Prevents Leaks

### What is Structured Concurrency?
**Principle**: Coroutines are organized in a hierarchy where parent coroutines automatically manage child coroutines' lifecycle.

### The Problem Without Structured Concurrency
```kotlin
// ❌ BAD - Potential leak with GlobalScope
class MyActivity : AppCompatActivity() {
    fun loadData() {
        GlobalScope.launch {
            // This keeps running even after activity is destroyed!
            val data = api.fetchLargeData()
            updateUI(data) // CRASH if activity destroyed
        }
    }
}
```

**Issues:**
- Coroutine outlives the Activity
- Memory leak (references destroyed Activity)
- Potential crash on UI update
- No automatic cancellation

### Solution 1: CoroutineScope Tied to Lifecycle
```kotlin
// ✅ GOOD - Automatic cancellation
class MyActivity : AppCompatActivity() {
    fun loadData() {
        lifecycleScope.launch {
            // Cancelled when activity is destroyed
            val data = api.fetchLargeData()
            updateUI(data) // Safe - cancelled if activity gone
        }
    }
}
```

### Solution 2: ViewModel Scope
```kotlin
// ✅ GOOD - Survives config changes, cancelled when cleared
class MyViewModel : ViewModel() {
    fun loadData() {
        viewModelScope.launch {
            // Cancelled when ViewModel.onCleared() is called
            _state.value = repository.getData()
        }
    }
}
```

### How Parent-Child Relationship Prevents Leaks

```kotlin
// Parent coroutine
lifecycleScope.launch { // Parent
    // Child coroutines
    launch { task1() } // Child 1
    launch { task2() } // Child 2
    
    async { task3() }.await() // Child 3
}
// When lifecycleScope is cancelled:
// 1. All children are cancelled automatically
// 2. Parent waits for children to finish cancellation
// 3. No orphaned coroutines
```

### Cancellation Propagation

```kotlin
// ✅ Proper structured concurrency
viewModelScope.launch {
    try {
        // All children cancelled if parent is cancelled
        val result1 = async { longRunningTask1() }
        val result2 = async { longRunningTask2() }
        
        updateUI(result1.await(), result2.await())
    } catch (e: CancellationException) {
        // Cleanup if needed
    }
}
```

**Guarantees:**
- **Parent cancelled → children cancelled**
- **Child fails → parent notified**
- **All or nothing** - no partial completion
- **Resource cleanup** - automatic

### Real-World Example: Search with Debounce

```kotlin
// ✅ Previous searches automatically cancelled
class SearchViewModel : ViewModel() {
    private var searchJob: Job? = null
    
    fun search(query: String) {
        // Cancel previous search
        searchJob?.cancel()
        
        searchJob = viewModelScope.launch {
            delay(300) // Debounce
            val results = withContext(Dispatchers.IO) {
                api.search(query)
            }
            _searchResults.value = results
        }
    }
    
    // searchJob cancelled automatically when ViewModel cleared
}
```

### Benefits of Structured Concurrency

1. **No Memory Leaks**
   - Coroutines tied to lifecycle
   - Automatic cleanup on scope cancellation

2. **No Orphaned Coroutines**
   - Parent tracks all children
   - Children die with parent

3. **Predictable Cancellation**
   - Clear hierarchy
   - Cancellation propagates down

4. **Exception Handling**
   - Child exceptions propagate to parent
   - Controlled error handling

5. **Resource Safety**
   - Files closed properly
   - Network connections released
   - Database transactions rolled back

### Anti-Pattern: Breaking Structured Concurrency
```kotlin
// ❌ BAD - Breaking structure with GlobalScope
lifecycleScope.launch {
    GlobalScope.launch {
        // This escapes the parent scope!
        // Won't be cancelled when lifecycleScope is cancelled
        longRunningTask()
    }
}

// ✅ GOOD - Keep structure
lifecycleScope.launch {
    launch {
        // Properly nested, will be cancelled with parent
        longRunningTask()
    }
}
```

### Summary

**Structured Concurrency Prevents Leaks By:**
- ✅ Automatic cancellation when scope ends
- ✅ Parent-child relationship management
- ✅ No orphaned background tasks
- ✅ Proper resource cleanup
- ✅ Clear lifecycle boundaries
- ✅ Exception propagation control

**Always Use:**
- `lifecycleScope` in Activities/Fragments
- `viewModelScope` in ViewModels
- `coroutineScope` for structured concurrency in suspend functions

**Never Use:**
- `GlobalScope` (except for true app-wide singletons)
- Unscoped `launch` or `async`

---

## Quick Reference Card

### Scope Selection
- **UI operations**: `lifecycleScope`
- **Business logic**: `viewModelScope`
- **Testing**: `TestScope` or `runTest`

### Dispatcher Selection
- **UI updates**: `Dispatchers.Main`
- **Network/DB**: `Dispatchers.IO`
- **CPU work**: `Dispatchers.Default`

### launch vs async
- **No return value**: `launch`
- **Need result**: `async` + `.await()`

### Error Handling
```kotlin
try {
    val result = withContext(Dispatchers.IO) { api.call() }
} catch (e: Exception) {
    handleError(e)
}
```

### Cancellation Safety
```kotlin
withContext(Dispatchers.IO) {
    ensureActive() // Check if cancelled
    // Do work
}
```
