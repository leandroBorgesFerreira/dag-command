package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.Config
import com.github.leandroborgesferreira.dagcommand.enums.ModuleType
import com.github.leandroborgesferreira.dagcommand.enums.PlugginType
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

fun parseAdjacencyList(project: Project, config: Config): Map<String, Set<String>> =
    project.subprojects
        .filter { subProject -> subProject.isModuleType(config.filter) }
        .map { subProject ->
            subProject.name to subProject.parseDependencies().map { dep -> dep.name }.toSet()
        }
        .toMap()
        .let(::revertAdjacencyList)

private fun Project.isModuleType(moduleType: ModuleType): Boolean {
    val isLibrary = project.plugins.hasPlugin(PlugginType.Library.value)
    val isApplication = project.plugins.hasPlugin(PlugginType.Application.value)

    return when (moduleType) {
        ModuleType.Library -> isLibrary
        ModuleType.Application -> isApplication
        else -> true
    }
}

private fun Project.parseDependencies(): List<Project> =
    project.configurations
        .flatMap { configuration ->
            configuration
                .dependencies
                .withType(ProjectDependency::class.java)
                .map { projectDependency ->
                    projectDependency.dependencyProject
                }
        }
