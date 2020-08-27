package com.github.leandroborgesferreira.dagcommand.domain

data class Node(val name: String, var buildStage: Int, val instability: Instability)

inline class NodeName(val value: String)
inline class Instability(val value: Double)

fun String.toNodeName(): NodeName {
    return NodeName(this)
}
