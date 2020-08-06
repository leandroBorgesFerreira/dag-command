package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.Frame

fun createDataFrame(adjacencyList: AdjacencyList): List<Frame> =
    adjacencyList.entries.flatMap { entry ->
        val source = entry.key

        entry.value.map { targetName ->
            Frame(source, targetName, 1)
        }
    }
