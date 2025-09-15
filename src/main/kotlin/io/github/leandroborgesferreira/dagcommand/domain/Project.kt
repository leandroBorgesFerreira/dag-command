package io.github.leandroborgesferreira.dagcommand.domain

data class DagProject(
    val fullGradlePath: String,
    val dependencies: Set<DagProject>
) {
    val lastName: String = fullGradlePath.split(":").last()
}
