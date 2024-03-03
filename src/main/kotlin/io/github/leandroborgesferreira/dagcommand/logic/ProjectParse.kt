package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

fun Project.toDagProjectList(): List<DagProject> = project.subprojects.map { project ->
    project.toDagProject()
}

private fun Project.toDagProject(): DagProject =
    DagProject(
        fullName = this.path,
        dependencies = parseDependencies().map { project ->
            project.toDagProject()
        }.toSet()
    )

private fun Project.parseDependencies(): List<Project> =
    project.configurations
        .flatMap { configuration ->
            configuration
                .dependencies
                .withType(ProjectDependency::class.java)
                .map { projectDependency -> projectDependency.dependencyProject }
                .filterNot { dependencyProject -> project.name == dependencyProject.name }
        }
