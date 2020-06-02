package br.com.leandroferreira.dagcommand.logic

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.enums.ModuleType
import br.com.leandroferreira.dagcommand.enums.PlugginType
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency

fun parseAdjacencyList(project: Project, config: Config) =
    project.subprojects.filter { subproject -> subproject.isModuleType(config.filter) }
        .flatMap(::dependencyMapping)
        .let(::getAdjacencyList)

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

private fun getAdjacencyList(dependencies: List<Pair<String, String>>) : Map<String, Set<String>> =
    dependencies
        .fold(mapOf()) { map, (project, dependency) ->
            val projectDependencies = map[project]?.plus(dependency) ?: setOf(dependency)

            map.plus(project to projectDependencies)
        }