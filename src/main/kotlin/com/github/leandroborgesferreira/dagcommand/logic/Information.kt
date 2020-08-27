package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.GraphInformation
import com.github.leandroborgesferreira.dagcommand.domain.Node

fun generalInformation(adjacencyList: AdjacencyList): GraphInformation {
    val nodeList: List<Node> = nodeList(adjacencyList)

    return GraphInformation(
        nodeCount = nodeList.size,
        edgeCount = createEdgeList(adjacencyList).size,
        buildStages = buildStageCount(nodeList),
        buildCoefficient = buildCoefficient(nodeList)
    )
}

private fun buildStageCount(nodeList: List<Node>) = nodeList.maxBy { node -> node.buildStage }!!.buildStage

private fun buildCoefficient(nodeList: List<Node>): Double =
    nodeList.fold(0) { acc, node -> acc + (node.buildStage + 1) } / nodeList.size.toDouble()
