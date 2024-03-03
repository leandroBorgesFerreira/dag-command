package io.github.leandroborgesferreira.dagcommand.adapter

import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.enums.ModuleType
import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import io.github.leandroborgesferreira.dagcommand.extension.CommandExtension

fun CommandExtension.parse(): Config =
    Config(
        filter = findFilter(filter),
        defaultBranch = defaultBranch,
        outputType = findOutputType(outputType),
        outputPath = outputPath,
        printModulesInfo = printModulesInfo
    )

private fun findFilter(filter: String): ModuleType =
    ModuleType.entries
        .find { enum -> enum.value.equals(filter, ignoreCase = true) }
        ?: throw IllegalStateException("This type of filter: $filter. Is not supported.")

private fun findOutputType(outputType: String): OutputType {
    val entries = OutputType.entries
    return OutputType.entries
        .find { enum -> enum.value.equals(outputType, ignoreCase = true) }
        ?: throw IllegalStateException(
            "This type of output: $outputType. Is not supported. Supported types are: ${entries.joinToString()}"
        )
}
