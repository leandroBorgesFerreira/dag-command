package com.github.leandroborgesferreira.dagcommand.adapter

import com.github.leandroborgesferreira.dagcommand.domain.Config
import com.github.leandroborgesferreira.dagcommand.enums.ModuleType
import com.github.leandroborgesferreira.dagcommand.enums.OutputType
import com.github.leandroborgesferreira.dagcommand.extension.CommandExtension

fun CommandExtension.parse() : Config =
    Config(findFilter(filter),
        defaultBranch,
        findOutputType(outputType),
        printAdjacencyList,
        printDataFrame
    )

private fun findFilter(filter: String): ModuleType =
    ModuleType
        .values()
        .find { enum -> enum.value.equals(filter, ignoreCase = true) }
        ?: throw IllegalStateException("This type of filter: $filter. Is not supported.")

private fun findOutputType(outputType: String): OutputType =
    OutputType
        .values()
        .find { enum -> enum.value.equals(outputType, ignoreCase = true) }
        ?: throw IllegalStateException("This type of output: $outputType. Is not supported.")
