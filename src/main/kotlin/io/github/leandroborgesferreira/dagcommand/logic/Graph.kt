package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import io.github.leandroborgesferreira.dagcommand.domain.exception.CycleDetectedException
import java.util.TreeSet

/**
 * This method checks the affected modules by traversing the modules using dynamic programming. If a cycle
 * is detected an [IllegalStateException] is thrown.
 *
 * @param adjacencyList [AdjacencyList] Representation of the graph.
 * @param changedModules Affected modules. Those are the initial positions of the traversal.
 */
fun affectedModules(adjacencyList: AdjacencyList, changedModules: List<String>): Set<String> {
    val resultSet: MutableSet<String> = mutableSetOf()

    changedModules.forEach { module ->
        traverseGraph(
            adjacencyList = adjacencyList,
            module = module,
            visited = TreeSet(),
            resultSet = resultSet
        )
    }

    return resultSet
}

/**
 * Recurvise method to traverse the modules graph.
 *
 * @param adjacencyList [AdjacencyList] Representation of the graph.
 * @param module The current node in the graph.
 * @param visited The current visited nodes of the current iteration. It a node that will be visited is already in
 * the Set, that means that the graph has a cycle.
 * @param resultSet The end result. The result is constructed as the navigation thought the graph happens.
 */
private fun traverseGraph(
    adjacencyList: AdjacencyList,
    module: String,
    visited: Set<String>,
    resultSet: MutableSet<String>
) {
    if (visited.contains(module)) {
        val graphString = "${visited.joinToString(separator = "->")}->$module"
        throw CycleDetectedException(
            "A cycle was detected in your graph. The part of the graph with the cycle is: $graphString"
        )
    }

    // If this node was already calculated in another traverse, there's no need to do it again because
    // everything bellow this node was already calculated.
    if (resultSet.contains(module)) return

    resultSet.add(module)

    adjacencyList[module]?.forEach { dependentModule ->
        traverseGraph(adjacencyList, dependentModule, visited + module, resultSet)
    }
}

internal fun <T> Iterable<T>.iterableToDagProjectT(
    filterModules: Set<String>,
    visitedT: Set<String>,
    getNext: (T) -> Iterable<T>,
    getPath: (T) -> String,
    getOutputName: (T) -> String
) = map { project -> project.toDagProjectT(filterModules, visitedT, getNext, getPath, getOutputName) }

internal fun <T> T.toDagProjectT(
    filterModules: Set<String>,
    visitedT: Set<String>,
    getNext: (T) -> Iterable<T>,
    getPath: (T) -> String,
    getOutputName: (T) -> String,
): DagProject {
    val nextDependencies = getNext(this)
    val nextNames = nextDependencies.map(getPath)
    val name = getOutputName(this)

    println(
        """
            Current module: $name
            Next names: ${nextNames.joinToString()}
        """
    )

    if (nextNames.any(visitedT::contains)) {
        val visitedAlready = nextNames.filter(visitedT::contains).distinct().joinToString()

        throw CycleDetectedException(
            """
                A cycle was detected in the graph of dependencies of your project.
                These modules appeared more than once: $visitedAlready. Trying to visit from: $name.
                All nodes visited already in this path: ${visitedT.joinToString()}
            """
        )
    }

    return DagProject(
        path = getPath(this),
        outputName = name,
        dependencies = nextDependencies.map { project ->
            project.toDagProjectT(filterModules, visitedT + name, getNext, getPath, getOutputName)
        }.toSet()
    )
}
