package io.github.leandroborgesferreira.dagcommand.domain

data class DagProject(
    val shortName: String,
    val displayName: String,
    val dependencies: Set<DagProject>
)
