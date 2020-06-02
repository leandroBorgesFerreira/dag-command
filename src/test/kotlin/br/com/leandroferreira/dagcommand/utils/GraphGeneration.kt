package br.com.leandroferreira.dagcommand.utils

fun simpleGraph(): Map<String, Set<String>> =
    mapOf(
        "A" to setOf("B", "C", "D"),
        "B" to setOf("C", "F"),
        "C" to setOf("E", "F"),
        "D" to setOf("E", "F"),
        "F" to emptySet()
    )

fun disconnectedGraph(): Map<String, Set<String>> =
    mapOf(
        "A" to emptySet(),
        "B" to emptySet(),
        "C" to emptySet(),
        "D" to emptySet(),
        "F" to emptySet()
    )
