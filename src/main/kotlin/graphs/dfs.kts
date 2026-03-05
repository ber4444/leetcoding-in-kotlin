package graphs

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
 * Depth-First Search (DFS) Implementation
 *
 * Time Complexity: O(V + E)
 * - V is the number of vertices/nodes.
 * - E is the number of edges.
 * - In the worst case, we visit every vertex and traverse every edge once (or twice for undirected).
 *
 * Space Complexity: O(V)
 * - We use a HashSet 'visited' to keep track of visited nodes, taking O(V) space.
 * - The Stack can also grow up to O(V) in the worst case (e.g., a path graph like A-B-C-D...).
 */
// almost the same as BF traversal except that here a Stack is used instead of a Queue
fun <T> dfs(graph: Graph<T>, startNode: T): String {
    val visited = mutableSetOf<T>()
    val stack = ArrayDeque<T>()
    val traversalList = mutableListOf<T>()

    stack.push(startNode)

    while (stack.isNotEmpty()) {
        val currentNode = stack.pop()
        if (currentNode !in visited) {
            traversalList.add(currentNode)
            visited.add(currentNode)
            // Push neighbors to stack
            graph.adjacencyMap[currentNode]?.forEach { stack.push(it) }
        }
    }

    return traversalList.joinToString()
}

println("--- Test 1: Original Graph ---")
val graph = Graph<Char>()
graph.addEdge('E', 'A')
graph.addEdge('A', 'B')
graph.addEdge('A', 'C')
graph.addEdge('C', 'D')
// start with an arbitrary node, and explore each branch completely ("depth first") before moving on to a new branch
// DFS is simpler than BFS and preferred when we want to visit every node
println("DFS from 'E': ${dfs(graph, 'E')}")

println("\n--- Test 2: Integer Graph with Cycle ---")
val cyclicGraph = Graph<Int>()
cyclicGraph.addEdge(1, 2)
cyclicGraph.addEdge(2, 3)
cyclicGraph.addEdge(3, 1)
cyclicGraph.addEdge(3, 4)
println("DFS from 1: ${dfs(cyclicGraph, 1)}")

println("\n--- Test 3: Disconnected Component ---")
val disconnectedGraph = Graph<String>()
disconnectedGraph.addEdge("Start", "Middle")
disconnectedGraph.addEdge("Middle", "End")
disconnectedGraph.addEdge("Unreachable", "Other")
println("DFS from 'Start': ${dfs(disconnectedGraph, "Start")}")
