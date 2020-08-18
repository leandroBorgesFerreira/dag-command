package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.utils.CommandExecutor

fun changedModules(commandExec: CommandExecutor, defaultBranch: String, adjacencyList: AdjacencyList): List<String> =
    commandExec.runCommand("git diff $defaultBranch --dirstat=files")
        .map(::parseModuleName)
        .filter(adjacencyList.keys::contains)
        .distinct()

private fun parseModuleName(commandResult: String): String =
    commandResult.trimStart()
        .split(" ", limit = 2)[1]
        .split("/", limit = 2)[0]
