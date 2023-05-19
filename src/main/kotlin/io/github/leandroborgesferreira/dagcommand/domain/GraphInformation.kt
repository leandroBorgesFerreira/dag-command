package io.github.leandroborgesferreira.dagcommand.domain

data class GraphInformation(
    val nodeCount: Int,
    val edgeCount: Int,
    val buildStages: Int,
    val buildCoefficient: Double
)
