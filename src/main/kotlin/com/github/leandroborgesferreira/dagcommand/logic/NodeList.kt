package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.Node
import java.util.*

fun findRootNodes(adjacencyList: AdjacencyList) = adjacencyList.keys - adjacencyList.values.flatten()

fun nodesData(adjacencyList: AdjacencyList): List<Node> {
    val edges = createEdgeList(adjacencyList)
    val nodeList: List<Node> = adjacencyList.keys.map { module ->
        Node(name = module, buildStage = 0, instability = calculateInstability(module, edges, adjacencyList.keys.size))
    }

    return calculateBuildStages(nodeList, adjacencyList)
}

fun List<Node>.groupByStages(): Map<Int, List<String>> =
    groupBy { buildStage ->
        buildStage.buildStage
    }.mapValues { (_, stageList) ->
        stageList.map { it.name }
    }.toSortedMap()

private fun calculateBuildStages(nodeList: List<Node>, adjacencyList: AdjacencyList): List<Node> {
    val modulesQueue: Queue<String> = LinkedList<String>().apply {
        addAll(findRootNodes(adjacencyList))
    }

    var currentStage = 0
    while (modulesQueue.isNotEmpty() && currentStage < 100) {
        nodeList.filter { (module, _) ->
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

    if (currentStage == 100) {
        println("Something when wrong when calculating the build stages of this project")
    }

    return nodeList
}
