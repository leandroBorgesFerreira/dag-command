package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

fun Project.toDagProjectList(filterModules: Set<String>): List<DagProject> =
    project.subprojects.map { project ->
        project.toDagProject(filterModules)
    }

private fun Project.toDagProject(filterModules: Set<String>): DagProject =
    DagProject(
        fullName = this.path,
        dependencies = parseDependencies(filterModules).map { project ->
            project.toDagProject(filterModules)
        }.toSet()
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


