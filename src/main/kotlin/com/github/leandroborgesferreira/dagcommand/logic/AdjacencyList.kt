package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList

fun revertAdjacencyList(adjacencyList: AdjacencyList): AdjacencyList {
    val resultMap = mutableMapOf<String, Set<String>>()

    adjacencyList.keys.forEach { moduleName ->
        resultMap[moduleName] = setOf()

        adjacencyList.entries.forEach { (name, dependencies) ->
            if (dependencies.contains(moduleName)) {
                resultMap[moduleName] = resultMap[moduleName]!!.plus(name)
            }
        }
    }

    return resultMap
}
