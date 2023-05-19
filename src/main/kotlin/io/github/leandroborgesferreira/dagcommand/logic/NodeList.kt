package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.Node
import java.util.*

fun findRootNodes(adjacencyList: AdjacencyList) = adjacencyList.keys - adjacencyList.values.flatten().toSet()

fun nodesData(adjacencyList: AdjacencyList): List<Node> {
    val totalModules = adjacencyList.keys.size
    val edges = createEdgeList(adjacencyList)
    val moduleToInstability = adjacencyList.keys.associateWith { module ->
        calculateInstability(module, edges, totalModules)
    }
    val phasesMap = compilePhases(adjacencyList).mapIndexed { i, modulesInPhase ->
        i to modulesInPhase
    }.toMap()
    val moduleToPhase = reversePhasesMap(phasesMap)

    val nodeList: List<Node> = adjacencyList.keys.map { module ->
        Node(
            name = module,
            buildStage = moduleToPhase[module] ?: -1,
            instability = moduleToInstability[module] ?: -1.0
        )
    }

    return nodeList
}

fun List<Node>.groupByStages(): Map<Int, List<String>> =
    groupBy { buildStage ->
        buildStage.buildStage
    }.mapValues { (_, stageList) ->
        stageList.map { it.name }
    }.toSortedMap()

private fun reversePhasesMap(phasesMap: Map<Int, Set<String>>): Map<String, Int> =
    phasesMap
        .flatMap { (key, values) -> values.map { it to key } }
        .associateBy({ it.first }, { it.second })
