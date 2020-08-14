package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.ModuleBuildStage
import java.util.*

fun affectedModules(adjacencyList: AdjacencyList, modules: List<String>): Set<String> {
    val resultSet: MutableSet<String> = mutableSetOf()

    modules.forEach { module ->
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

fun findRootNodes(adjacencyList: AdjacencyList) = adjacencyList.keys - adjacencyList.values.flatten()

fun buildOrder(adjacencyList: AdjacencyList): Map<Int, List<String>> {
    val modulesQueue: Queue<String> = LinkedList<String>().apply {
        addAll(findRootNodes(adjacencyList))
    }

    var currentStage = 0
    val moduleWithStage: List<ModuleBuildStage> = adjacencyList.keys.map { module -> ModuleBuildStage(module) }

    while (modulesQueue.isNotEmpty()) {
        moduleWithStage.filter { (module, _) ->
            modulesQueue.contains(module)
        }.forEach { moduleBuildStage ->
            moduleBuildStage.stage = currentStage
        }

        val modulesOfNextLevel = modulesQueue.mapNotNull { module ->
            adjacencyList[module]
        }.reduce { acc, set -> acc + set }

        modulesQueue.clear()
        modulesQueue.addAll(modulesOfNextLevel)
        currentStage++
    }

    return moduleWithStage.groupBy { buildStage ->
        buildStage.stage
    }.mapValues { (_, stageList) ->
        stageList.map { it.module }
    }.toSortedMap()
}


