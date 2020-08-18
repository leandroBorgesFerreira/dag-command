package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.Node
import java.util.*

fun findRootNodes(adjacencyList: AdjacencyList) = adjacencyList.keys - adjacencyList.values.flatten()

fun nodeList(adjacencyList: AdjacencyList): List<Node> {
    val modulesQueue: Queue<String> = LinkedList<String>().apply {
        addAll(findRootNodes(adjacencyList))
    }

    var currentStage = 0
    val moduleWithStage: List<Node> = adjacencyList.keys.map { module -> Node(module, 0) }

    while (modulesQueue.isNotEmpty()) {
        moduleWithStage.filter { (module, _) ->
            modulesQueue.contains(module)
        }.forEach { moduleBuildStage ->
            moduleBuildStage.buildStage = currentStage
        }

        val modulesOfNextLevel = modulesQueue.mapNotNull { module ->
            adjacencyList[module]
        }.reduce { acc, set -> acc + set }

        modulesQueue.clear()
        modulesQueue.addAll(modulesOfNextLevel)
        currentStage++
    }

    return moduleWithStage
}

fun List<Node>.groupByStages() : Map<Int, List<String>> =
    groupBy { buildStage ->
        buildStage.buildStage
    }.mapValues { (_, stageList) ->
        stageList.map { it.name }
    }.toSortedMap()
