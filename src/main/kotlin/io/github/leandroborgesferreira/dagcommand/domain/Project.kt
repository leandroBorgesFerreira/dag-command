package io.github.leandroborgesferreira.dagcommand.domain

data class DagProject(
    val path: String,
    val outputName: String,
    val dependencies: Set<DagProject>
) {
    val lastName: String = path.split(":").last()
}
