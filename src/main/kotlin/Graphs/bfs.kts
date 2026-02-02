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

// not recursive, just uses a queue and needs a "visited" flag so that we don't backtrack and revisit nodes
fun <T> bfs(graph: Graph<T>, startNode: T): String {
    val traversalList = mutableListOf<T>()
    val visited = mutableSetOf<T>()
    val queue = ArrayDeque<T>()

    queue.add(startNode)

    while (queue.isNotEmpty()) {
        val currentNode = queue.removeFirst()
        if (currentNode !in visited) {
            traversalList.add(currentNode)
            visited.add(currentNode)
            graph.adjacencyMap[currentNode]?.forEach { queue.add(it) }
        }
    }

    return traversalList.toString()
}

val graph = Graph<Char>()
graph.addEdge('E', 'A')
graph.addEdge('A', 'B')
graph.addEdge('A', 'C')
graph.addEdge('C', 'D')
// start with an arbitrary node, and explore each neighbor ("breath first") before moving on to their children - moving level by level
// BFS is typically better than DFS for path finding
// Dijkstra's algorithm (which can be bidirectional as well) is used for finding shortest path
println(bfs(graph, 'E'))