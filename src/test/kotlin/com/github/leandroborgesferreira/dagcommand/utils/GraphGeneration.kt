package com.github.leandroborgesferreira.dagcommand.utils

import com.github.leandroborgesferreira.dagcommand.domain.Edge

fun simpleGraph(): Map<String, Set<String>> =
    mapOf(
        "A" to setOf("B", "C", "D"),
        "B" to setOf("C", "F"),
        "C" to setOf("E", "F"),
        "D" to setOf("E", "F"),
        "E" to setOf("F"),
        "F" to emptySet()
    )

fun simpleEdgeList(): List<Edge> =
    listOf(
        Edge("A", "B", 1),
        Edge("A", "C", 1),
        Edge("A", "D", 1),
        Edge("B", "C", 1),
        Edge("B", "F", 1),
        Edge("C", "E", 1),
        Edge("C", "F", 1),
        Edge("D", "E", 1),
        Edge("D", "F", 1),
        Edge("E", "F", 1)
    )

fun disconnectedGraph(): Map<String, Set<String>> =
    mapOf(
        "A" to emptySet(),
        "B" to emptySet(),
        "C" to emptySet(),
        "D" to emptySet(),
        "F" to emptySet()
    )
