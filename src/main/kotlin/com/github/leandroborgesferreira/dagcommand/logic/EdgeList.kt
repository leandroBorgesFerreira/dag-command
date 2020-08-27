package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.Edge
import com.github.leandroborgesferreira.dagcommand.domain.Instability
import com.github.leandroborgesferreira.dagcommand.domain.Node
import com.github.leandroborgesferreira.dagcommand.domain.NodeName

fun createEdgeList(adjacencyList: AdjacencyList): List<Edge> =
        adjacencyList.entries.flatMap { entry ->
            val source = entry.key

            entry.value.map { targetName ->
                Edge(source, targetName, 1)
            }
        }

typealias Edges = List<Edge>

fun Edges.calculateInstabiltyForNode(node: NodeName): Instability {
    var outDependencies = 0
    var totalDependecies = 0

    forEach { edge ->
        if (edge.target == node.value) {
            outDependencies++
            totalDependecies++
        } else if (edge.source == node.value) {
            totalDependecies++
        }
    }

    return Instability(outDependencies.toDouble() / totalDependecies)
}

