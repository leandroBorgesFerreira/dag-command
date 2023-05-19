package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.Edge

fun createEdgeList(adjacencyList: AdjacencyList): List<Edge> =
    adjacencyList.entries.flatMap { entry ->
        val source = entry.key

        entry.value.map { targetName ->
            Edge(source, targetName, 1)
        }
    }

