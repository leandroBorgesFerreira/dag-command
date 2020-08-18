package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList

fun affectedModules(adjacencyList: AdjacencyList, changedModules: List<String>): Set<String> {
    val resultSet: MutableSet<String> = mutableSetOf()

    changedModules
        .forEach { module ->
            traverseGraph(adjacencyList, module, resultSet)
        }

    return resultSet
}

private fun traverseGraph(adjacencyList: AdjacencyList, module: String, resultSet: MutableSet<String>) {
    resultSet.add(module)

    adjacencyList[module]?.forEach { dependentModule ->
        traverseGraph(adjacencyList, dependentModule, resultSet)
    }
}
