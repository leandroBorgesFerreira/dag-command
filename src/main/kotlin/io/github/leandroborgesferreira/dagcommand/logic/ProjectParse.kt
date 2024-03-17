package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

fun Project.toDagProjectList(filterModules: Set<String>): List<DagProject> =
    project.subprojects.iterableToDagProjectT(
        filterModules = filterModules,
        visitedT = emptySet(),
        getNext = { project -> project.parseDependencies(filterModules) },
        getName = { project -> project.name },
    )

private fun Project.parseDependencies(filterModules: Set<String>): List<Project> {
    return project.configurations
        .flatMap { configuration ->
            configuration
                .dependencies
                .withType(ProjectDependency::class.java)
                .map { projectDependency -> projectDependency.dependencyProject }
                .filterNot { dependencyProject ->
                    val dependencyName = dependencyProject.name
                    project.name == dependencyName || filterModules.contains(dependencyName)
                }
        }
}


