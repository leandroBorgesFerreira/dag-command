package io.github.leandroborgesferreira.dagcommand.adapter

import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.enums.ModuleType
import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import io.github.leandroborgesferreira.dagcommand.extension.CommandExtension

fun CommandExtension.parse(): Config =
    Config(
        filter = io.github.leandroborgesferreira.dagcommand.adapter.findFilter(filter),
        defaultBranch = defaultBranch,
        outputType = io.github.leandroborgesferreira.dagcommand.adapter.findOutputType(outputType),
        outputPath = outputPath,
        printModulesInfo = printModulesInfo
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
