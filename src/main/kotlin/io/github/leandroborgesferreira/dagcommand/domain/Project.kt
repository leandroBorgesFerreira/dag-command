package io.github.leandroborgesferreira.dagcommand.domain

data class DagProject(
    val fullName: String,
    val dependencies: Set<DagProject>
) {
    val lastName: String = fullName.split(":").last()
}
