package Coroutines

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

var shared: Int = 0
val lock = Object()
fun increment(): Int {
    Thread.sleep(10)
    synchronized(lock) { // to avoid race conditions where multiple threads would write to it at once
                         // deadlock: 2 threads are waiting for each other to release a lock
        shared++
    }
    return shared
}
val shared2 = AtomicInteger(0)

val executor: ExecutorService? = Executors.newFixedThreadPool(10)
repeat (10) {
    executor?.execute {
        shared2.incrementAndGet()
        increment()
    }
}
executor?.shutdown()
executor?.awaitTermination(1, TimeUnit.MINUTES)
println("a $shared")
println("b $shared2")

// remember that LinkedList is not synchronized
// you can do Collections.synchronizedList(list) to make it synchronized
// ArrayDequeue is also not thread safe, but Stack is thread safe
// HashMap is also not synchronized, but Hashtable is thread-safe
