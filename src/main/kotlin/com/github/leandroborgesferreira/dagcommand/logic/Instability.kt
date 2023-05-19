package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.Edge

fun calculateInstability(node: String, edgeList: List<Edge>, totalModules: Int): Double =
    edgeList.count { edge -> edge.target == node }.toDouble() / totalModules
