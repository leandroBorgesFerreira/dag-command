package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList

fun affectedModules(adjacencyList: AdjacencyList, changedFolders: List<String>): Set<String> {
    val resultSet: MutableSet<String> = mutableSetOf()

    changedFolders
        .filter { folder -> adjacencyList.keys.contains(folder) }
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
