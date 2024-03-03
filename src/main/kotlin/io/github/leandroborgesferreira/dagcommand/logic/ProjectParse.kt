package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.domain.DagProject
import io.github.leandroborgesferreira.dagcommand.enums.ModuleType
import io.github.leandroborgesferreira.dagcommand.enums.PlugginType
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


private fun Project.isModuleType(moduleType: ModuleType): Boolean {
    println("Project: ${project.displayName}")

    val isAndroidLibrary = project.plugins.hasPlugin(PlugginType.ANDROID_LIBRARY.value)
    val isAndroidApplication = project.plugins.hasPlugin(PlugginType.ANDROID_APPLICATION.value)

    return when (moduleType) {
        ModuleType.Library -> isAndroidLibrary
        ModuleType.Application -> isAndroidApplication
        ModuleType.All -> true
    }
}

fun parseAdjacencyList(project: Project, config: Config): Map<String, Set<String>> =
    project.subprojects
        .filter { subProject -> subProject.isModuleType(config.filter) }
        .associate { subProject ->
            subProject.displayName to subProject.parseDependencies().map { dep -> dep.name }.toSet()
        }
        .let(::revertAdjacencyList)

