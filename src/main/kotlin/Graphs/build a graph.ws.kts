package Graphs

/**
 * Undirected Graph implementation using an Adjacency Map.
 *
 * Space Complexity: O(V + E)
 * - V is the number of vertices (nodes).
 * - E is the number of edges.
 * - We store each node in the map.
 * - Since the graph is undirected, each edge (u, v) is stored twice: once in u's neighbor set and once in v's neighbor set.
 */
class Graph<T> {
    // maps nodes to adjacent nodes (nodes are also called vertices)
    private val adjacencyMap: MutableMap<T, MutableSet<T>> = mutableMapOf()

    /**
     * Adds an edge to the graph.
     *
     * Time Complexity: O(1) on average
     * - HashMap `computeIfAbsent` and access is O(1) amortized.
     * - HashSet `add` is O(1) amortized.
     */
    fun addEdge(source: T, dest: T) {
        adjacencyMap.computeIfAbsent(source) { mutableSetOf() }.add(dest)
        // in this case the graph is undirected, meaning that a connection is stored both at source and dest
        adjacencyMap.computeIfAbsent(dest) { mutableSetOf() }.add(source)
    }

    override fun toString() = buildString {
        for ((key, neighbors) in adjacencyMap) {
            append("$key -> $neighbors\n")
        }
    }
}

/*
visually:
 E
 |
 A - B
which is stored in memory as:
A -> [B, E]
B -> [A]
E -> [A]
(node: if it's an undirected graph, we simply store all connections twice: a->b and b->a)
 */

// --- Tests ---

println("--- Test 1: Simple Character Graph ---")
val graph = Graph<Char>()
graph.addEdge('E', 'A')
graph.addEdge('A', 'B')
println(graph.toString())

println("--- Test 2: Integer Graph with Cycle (Triangle) ---")
val intGraph = Graph<Int>()
intGraph.addEdge(1, 2)
intGraph.addEdge(2, 3)
intGraph.addEdge(3, 1)
println(intGraph.toString())

println("--- Test 3: Disconnected Graph ---")
val disconnectedGraph = Graph<String>()
disconnectedGraph.addEdge("Cat", "Dog")
disconnectedGraph.addEdge("Bird", "Worm")
println(disconnectedGraph.toString())
