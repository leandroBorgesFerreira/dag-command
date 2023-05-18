package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.Node
import java.util.*

fun findRootNodes(adjacencyList: AdjacencyList) = adjacencyList.keys - adjacencyList.values.flatten().toSet()

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

    val expandedNodes = mutableSetOf<String>()

    var currentStage = 0

    while (modulesQueue.isNotEmpty()) {
        /* First takes all the modules in the current stage and write their build stage */
        nodeList.filter { (module, _) ->
            modulesQueue.contains(module)
        }.forEach { moduleBuildStage ->
            moduleBuildStage.buildStage = currentStage
        }

        // Now, go to the next stage of the dag for each module.
        val modulesOfNextLevel = modulesQueue
            .filter { module -> !expandedNodes.contains(module) }
            .mapNotNull { module ->
            adjacencyList[module]
        }.reduce { acc, set -> acc + set }

        expandedNodes.addAll(modulesQueue)

        modulesQueue.clear()
        modulesQueue.addAll(modulesOfNextLevel)
        currentStage++

        if (currentStage >= 100) {
            throw IllegalStateException("The build stage hit the 100 stage looks like the library hit a loop while " +
                    "traversing the graph of dependencies.")
        }
    }

    return nodeList
}
