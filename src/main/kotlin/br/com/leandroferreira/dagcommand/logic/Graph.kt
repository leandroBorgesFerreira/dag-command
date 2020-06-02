package br.com.leandroferreira.dagcommand.logic

fun affectedModules(adjacencyList: AdjacencyList, modules: List<String>): Set<String> {
    val resultSet: MutableSet<String> = mutableSetOf()

    modules.forEach { module ->
        traverseGraph(adjacencyList, module, resultSet)
    }

    return resultSet
}

private fun traverseGraph(adjacencyList: AdjacencyList, module: String, resultSet: MutableSet<String>) {
    resultSet.add(module)

    val dependentModules: Set<String>? = adjacencyList[module]

    dependentModules?.let(resultSet::addAll)

    dependentModules?.forEach { dependentModule ->
        traverseGraph(adjacencyList, dependentModule, resultSet)
    }
}
