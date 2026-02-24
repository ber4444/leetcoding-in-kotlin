JS: https://gist.github.com/ber4444/978a59ffb6ef957a58da5dd80c9bd014

TS: https://gist.github.com/ber4444/f321cbbb0a892a442d9e01aeb637059f

Python: https://gist.github.com/ber4444/8356777204ba68de0bef5c8ac8dfaaa8

Unlike in JS/python, Kotlin Coroutines can share the same objects. (You must still use synchronization primitives (like Mutex or Atomic variables or immutable data structures to prevent race conditions.)
This is where kotlin shines compared to python and typescript which are single-threaded.

In TS/JS async/await cannot parallelize CPU work, if you run a heavy math calculation, the whole app freezes.
Node.js worker threads let you run multiple threads with their own memory and engine instances.

In python, the GIL prevents multiple threads from executing Python bytecode at the same time;
so python has only a multiprocessing module which allows parallel execution but each process will have its own memory and own python interpreter.


So kotlin allows passing large data structures between parallel tasks nearly instantaneous
compared to the "serialization/deserialization" dance in JS. It's also 10x to 100x faster than python.
Python only feels "fast" in data science because libraries like NumPy are actually written in C.

So Python is still king in ML research/model training and JS is king in full stack web/cloud AI apps, but Kotlin and KMP is a safer bet for mobile (native speed).

Specific strengths of Kotlin that TS/Python lacks:
1. compile time null safety,
2. speed and less memory (KMP compiles to LLVM bytecode on iOS and JVM bytecode on Android;
   Android ART uses hybrid JIT/AOT and KMP on iOS uses Ahead-of-Time while JS/TS is JIT causing "warm-up" spikes)
3. kotlin sealed classes allow compile-time exhaustive state management
4. kotlin has immutable data classes built in
5.  Dispatchers.IO is for I/O bound tasks such as network requests, database queries, and file I/O.
    TS and Python are ok-ish for those, with promises and asyncio,
    but they still lack structured concurrency. (_Update, Python 3.11+_ introduced asyncio.TaskGroup, which provides structured concurrency very similar to Kotlin.) Coroutines must be launched in a Scope. If the scope is cancelled
    (e.g., ViewModel is cleared or a Network request times out), all child coroutines are automatically killed.
    In TS (Promises) or Python (asyncio), if you start a background task and the user closes the screen,
    that task might keep running in the background (a "zombie" or orphaned task).
6. In Kotlin, a reified function allows you to check types at runtime. In TS and Python, generics are erased at runtime.
 
