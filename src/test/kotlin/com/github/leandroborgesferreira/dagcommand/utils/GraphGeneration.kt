package com.github.leandroborgesferreira.dagcommand.utils

import com.github.leandroborgesferreira.dagcommand.domain.Frame

fun simpleGraph(): Map<String, Set<String>> =
    mapOf(
        "A" to setOf("B", "C", "D"),
        "B" to setOf("C", "F"),
        "C" to setOf("E", "F"),
        "D" to setOf("E", "F"),
        "F" to emptySet()
    )

fun simpleDataFrame(): List<Frame> =
    listOf(
        Frame("A", "B", 1),
        Frame("A", "C", 1),
        Frame("A", "D", 1),
        Frame("B", "C", 1),
        Frame("B", "F", 1),
        Frame("C", "E", 1),
        Frame("C", "F", 1),
        Frame("D", "E", 1),
        Frame("D", "F", 1)
    )

fun disconnectedGraph(): Map<String, Set<String>> =
    mapOf(
        "A" to emptySet(),
        "B" to emptySet(),
        "C" to emptySet(),
        "D" to emptySet(),
        "F" to emptySet()
    )
