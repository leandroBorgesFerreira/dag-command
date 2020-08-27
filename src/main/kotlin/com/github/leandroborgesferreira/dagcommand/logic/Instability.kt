package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.Edge

fun calculateInstability(node: String, edgeList: List<Edge>, totalModules: Int): Double =
    edgeList
        .filter { edge -> edge.target == node }
        .count()
        .toDouble() / totalModules
