package Graphs

import java.util.ArrayDeque

class Graph<T> {
    val adjacencyMap: MutableMap<T, MutableSet<T>> = mutableMapOf()
    fun addEdge(source: T, dest: T) {
        adjacencyMap.computeIfAbsent(source) { mutableSetOf() }.add(dest)
        // in this case the graph is undirected, meaning that a connection is stored both at source and dest
        adjacencyMap.computeIfAbsent(dest) { mutableSetOf() }.add(source)
    }
}

/**
 * Breadth-First Search (BFS) implementation.
 *
 * Time Complexity: O(V + E)
 * - V is the number of vertices.
 * - E is the number of edges.
 * - We visit every vertex at most once and traverse every edge at most twice (once from each end).
 *
 * Space Complexity: O(V)
 * - O(V) for the visited set.
 * - O(V) for the queue (in the worst case).
 * - O(V) for the traversal result list.
 */
fun <T> bfs(graph: Graph<T>, startNode: T): List<T> {
    val traversalList = mutableListOf<T>()
    val visited = mutableSetOf<T>()
    val queue = ArrayDeque<T>()

    // Add start node to queue and mark as visited immediately
    queue.add(startNode)
    visited.add(startNode)

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()
        traversalList.add(currentNode)

        graph.adjacencyMap[currentNode]?.forEach { neighbor ->
            if (neighbor !in visited) {
                visited.add(neighbor)
                queue.add(neighbor)
            }
        }
    }

    return traversalList
}

val graph = Graph<Char>()
graph.addEdge('E', 'A')
graph.addEdge('A', 'B')
graph.addEdge('A', 'C')
graph.addEdge('C', 'D')

// start with an arbitrary node, and explore each neighbor ("breadth first") before moving on to their children - moving level by level
// BFS is typically better than DFS for path finding
// Dijkstra's algorithm (which can be bidirectional as well) is used for finding shortest path
val result = bfs(graph, 'E')
println("Result: $result")

// Simple test to verify implementation
val expected = listOf('E', 'A', 'B', 'C', 'D')
if (result == expected) {
    println("Test PASSED")
} else {
    println("Test FAILED. Expected $expected but got $result")
}
