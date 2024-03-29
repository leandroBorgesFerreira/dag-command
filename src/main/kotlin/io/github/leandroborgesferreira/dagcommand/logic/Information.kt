package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.Edge
import io.github.leandroborgesferreira.dagcommand.domain.GraphInformation
import io.github.leandroborgesferreira.dagcommand.domain.Node

fun generalInformation(
    adjacencyList: AdjacencyList,
    nodeList: List<Node> = nodesData(adjacencyList),
    edgeList: List<Edge> = createEdgeList(adjacencyList)
): GraphInformation {
    return GraphInformation(
        nodeCount = nodeList.size,
        edgeCount = edgeList.size,
        buildStages = buildStageCount(nodeList),
        buildCoefficient = buildCoefficient(nodeList)
    )
}

private fun buildStageCount(nodeList: List<Node>): Int =
    nodeList.takeIf { it.isNotEmpty() }?.maxBy { node -> node.buildStage }?.buildStage ?: -1

private fun buildCoefficient(nodeList: List<Node>): Double =
    nodeList.fold(0) { acc, node -> acc + (node.buildStage + 1) } / nodeList.size.toDouble()
