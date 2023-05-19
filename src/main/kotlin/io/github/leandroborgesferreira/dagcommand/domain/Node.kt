package io.github.leandroborgesferreira.dagcommand.domain

data class Node(val name: String, var buildStage: Int, val instability: Double)
