package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList

fun compilePhases(adjacencyList: AdjacencyList): MutableList<MutableSet<String>> {
    // First, create a topologically sorted version of the graph
    val sortedGraph = topologicalSort(adjacencyList)
    val phases: MutableList<MutableSet<String>> = mutableListOf(mutableSetOf(sortedGraph.first()))

    sortedGraph.forEach { currentModule ->
        val currentPhase = phases.last()
        /* Expands the modules of last phase to find if current module is in the next phase or is inside the
         * current one. */
        val isNeighbor = currentPhase.any { module -> adjacencyList[module]?.contains(currentModule) == true }
        if (isNeighbor) {
            /*
             * If the node is a neighbor, that means it is the next level of the compilation because it depends on a
             * a module of the previous phase.
             */
            phases.add(mutableSetOf(currentModule))
        } else {
            // If the node is NOT a neighbor, that means it is in the same level of compilation
            currentPhase.add(currentModule)
        }
    }

    return phases
}

fun topologicalSort(graph: Map<String, Set<String>>): List<String> {
    val sortedOrder = mutableListOf<String>()
    val visited = mutableSetOf<String>()

    graph.keys.forEach { vertex ->
        if (!visited.contains(vertex)) {
            deepFirstSearch(vertex, graph, visited, sortedOrder)
        }
    }

    sortedOrder.reverse()
    return sortedOrder
}

private fun deepFirstSearch(
    vertex: String,
    graph: Map<String, Set<String>>,
    visited: MutableSet<String>,
    sortedOrder: MutableList<String>
) {
    visited.add(vertex)

    (graph[vertex] ?: emptySet()).forEach { neighbor ->
        if (!visited.contains(neighbor)) {
            deepFirstSearch(neighbor, graph, visited, sortedOrder)
        }
    }

    sortedOrder.add(vertex)
}