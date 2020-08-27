package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.Node
import com.github.leandroborgesferreira.dagcommand.domain.toNodeName
import java.util.LinkedList
import java.util.Queue

fun findRootNodes(adjacencyList: AdjacencyList) = adjacencyList.keys - adjacencyList.values.flatten()

fun nodeList(adjacencyList: AdjacencyList): List<Node> {
    val edges = createEdgeList(adjacencyList)
    val modulesQueue: Queue<String> = LinkedList<String>().apply {
        addAll(findRootNodes(adjacencyList))
    }

    var currentStage = 0
    val moduleWithStage: List<Node> = adjacencyList.keys.map { module ->
        Node(
                name = module,
                buildStage = 0,
                instability = edges.calculateInstabiltyForNode(node = module.toNodeName())
        )
    }

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

fun List<Node>.groupByStages(): Map<Int, List<String>> =
        groupBy { buildStage ->
            buildStage.buildStage
        }.mapValues { (_, stageList) ->
            stageList.map { it.name }
        }.toSortedMap()
