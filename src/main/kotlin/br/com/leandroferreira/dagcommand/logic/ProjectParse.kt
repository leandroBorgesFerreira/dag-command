package br.com.leandroferreira.dagcommand.logic

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.enums.ModuleType
import br.com.leandroferreira.dagcommand.enums.PlugginType
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

fun parseAdjacencyList(project: Project, config: Config) =
    project.subprojects.filter { subProject -> subProject.isModuleType(config.filter) }
        .flatMap(::dependencyMapping) //We get dependencies not dependents.
        .let(::getAdjacencyList) //Our adjacency list is reversed
        .let(::revertAdjacencyList) //Revert to normal adjacency list.

private fun Project.isModuleType(moduleType: ModuleType): Boolean {
    val isLibrary = project.plugins.hasPlugin(PlugginType.Library.value)
    val isApplication = project.plugins.hasPlugin(PlugginType.Application.value)

    return when (moduleType) {
        ModuleType.Library -> isLibrary
        ModuleType.Application -> isApplication
        else -> isLibrary || isApplication
    }
}

private fun dependencyMapping(project: Project): List<Pair<String, String>> =
    project.configurations.map { project to it }
        .flatMap { (project, configuration) ->
            configuration
                .dependencies
                .withType(ProjectDependency::class.java)
                .map { project to it.dependencyProject }
        }
        .map { (project, dependency) -> project.name to dependency.name }
