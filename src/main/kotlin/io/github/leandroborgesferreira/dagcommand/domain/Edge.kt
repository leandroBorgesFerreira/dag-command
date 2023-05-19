package io.github.leandroborgesferreira.dagcommand.domain

data class Edge(
    val source: String,
    val target: String,
    val weight: Int
)
