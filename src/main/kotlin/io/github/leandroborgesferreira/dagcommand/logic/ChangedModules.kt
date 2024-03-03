package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.utils.CommandExecutor

private const val BUILD_SRC = "buildSrc"

fun changedModules(
    commandExec: CommandExecutor,
    defaultBranch: String,
    adjacencyList: AdjacencyList
): List<String> =
    commandExec.runCommand("git diff $defaultBranch --dirstat=files,0")
        .map(::parseModuleName)
        .let { modules ->
            if (modules.contains(BUILD_SRC)) {
                adjacencyList.keys
            } else {
                modules.filter(adjacencyList.keys::contains)
            }.distinct()
        }


private fun parseModuleName(commandResult: String): String {
    val fullPath = commandResult.trimStart().split(" ", limit = 2)[1]

    return when {
        fullPath.contains("/src") ->
            ":${fullPath.split("/src", limit = 2)[0].replace("/", ":")}"
        
        fullPath.contains("/") -> ":${fullPath.split("/", limit = 2)[0]}"

        else -> ""
    }
}

