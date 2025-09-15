package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.DagProject

fun parseAdjacencyList(projects: Iterable<DagProject>): Map<String, Set<String>> =
    projects
        .let(::filterModules)
        .associate { subProject ->
            val dependencies = subProject.dependencies.map { dep -> dep.fullGradlePath }.toSet()
            subProject.fullGradlePath to dependencies
        }.let(::revertAdjacencyList)

private fun filterModules(projects: Iterable<DagProject>): Iterable<DagProject> {
    // All words present in the modules, divided by ':'
    val allWords = projects.map { project -> project.fullGradlePath }
        .flatMap { name -> name.split(":") }

    /* When a word appears more than once, it means that it is actually a part of a path, not a
    * module. */
    val nonModulesWords = allWords.fold(emptyMap<String, Int>()) { countMap, word ->
        val newCount = countMap[word]?.plus(1) ?: 1
        countMap + (word to newCount)
    }.filter { (_, count) ->
        count > 1
    }.keys

    return projects.filter { project -> !nonModulesWords.contains(project.lastName) }
}

fun revertAdjacencyList(adjacencyList: AdjacencyList): AdjacencyList {
    val resultMap = mutableMapOf<String, Set<String>>()

    adjacencyList.keys.forEach { moduleName ->
        resultMap[moduleName] = setOf()

        adjacencyList.entries.forEach { (name, dependencies) ->
            if (dependencies.contains(moduleName)) {
                resultMap[moduleName] = resultMap[moduleName]!!.plus(name)
            }
        }
    }

    return resultMap
}
