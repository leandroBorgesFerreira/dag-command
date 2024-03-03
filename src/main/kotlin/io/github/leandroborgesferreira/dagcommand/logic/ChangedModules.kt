package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.utils.CommandExecutor

private const val BUILD_SRC = ":buildSrc"

fun changedModules(
    commandExec: CommandExecutor,
    defaultBranch: String,
    adjacencyList: AdjacencyList
): List<String> =
    commandExec.runCommand("git diff $defaultBranch --dirstat=files,0")
        .flatMap(::parseModuleName)
        .toSet()
        .let { modules ->
            if (modules.contains(BUILD_SRC)) {
                adjacencyList.keys
            } else {
                modules.filter(adjacencyList.keys::contains)
            }.distinct()
        }


private fun parseModuleName(commandResult: String): Set<String> {
    val fullPath = commandResult.trimStart().split(" ", limit = 2)[1]

    val words = fullPath.split("/")
        .takeWhile { word ->
            word != "src"
        }
        .filter { it.isNotEmpty() }

    val result: Set<String> = words.fold(emptySet()) { acc, word ->
        if (acc.isNotEmpty()) {
            val lasWord = acc.last()
            acc + "$lasWord:$word"
        } else {
            setOf(":$word")
        }
    }

    return result
}
