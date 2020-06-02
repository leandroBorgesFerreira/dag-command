package br.com.leandroferreira.dagcommand.logic

typealias AdjacencyList = Map<String, Set<String>>

fun getAdjacencyList(dependencies: List<Pair<String, String>>) : AdjacencyList =
    dependencies
        .fold(mapOf()) { map, (project, dependency) ->
            val projectDependencies = map[project]?.plus(dependency) ?: setOf(dependency)

            map.plus(project to projectDependencies)
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
