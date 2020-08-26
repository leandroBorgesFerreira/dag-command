package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.utils.CommandExecutor

private const val BUILD_SRC = "buildSrc"

fun changedModules(commandExec: CommandExecutor, defaultBranch: String, adjacencyList: AdjacencyList): List<String> =
    commandExec.runCommand("git diff $defaultBranch --dirstat=files")
        .map(::parseModuleName)
        .let { modules ->
            if (modules.contains(BUILD_SRC)) {
                adjacencyList.keys
            } else {
                modules.filter(adjacencyList.keys::contains)
            }.distinct()
        }


private fun parseModuleName(commandResult: String): String =
    commandResult.trimStart()
        .split(" ", limit = 2)[1]
        .split("/", limit = 2)[0]
