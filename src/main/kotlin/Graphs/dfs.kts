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

// this one uses a Stack (or can be written recursively)
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
            graph.adjacencyMap[currentNode]?.forEach { stack.push(it) }
        }
    }

    return traversalList.joinToString()
}

val graph = Graph<Char>()
graph.addEdge('E', 'A')
graph.addEdge('A', 'B')
graph.addEdge('A', 'C')
graph.addEdge('C', 'D')
// start with an arbitrary node, and explore each branch completely ("depth first") before moving on to a new branch
// DFS is simpler than BFS and preferred when we want to visit every node
println(dfs(graph, 'E'))