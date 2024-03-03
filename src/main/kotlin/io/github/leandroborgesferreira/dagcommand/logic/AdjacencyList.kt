package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.DagProject

fun parseAdjacencyList2(projects: Iterable<DagProject>): Map<String, Set<String>> =
    projects
        .let(::filterModules)
        .associate { subProject ->
            val dependencies = subProject.dependencies.map { dep -> dep.displayName }.toSet()
            subProject.displayName to dependencies
        }.let(::revertAdjacencyList)

private fun filterModules(projects: Iterable<DagProject>): Iterable<DagProject> {
    return projects
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
