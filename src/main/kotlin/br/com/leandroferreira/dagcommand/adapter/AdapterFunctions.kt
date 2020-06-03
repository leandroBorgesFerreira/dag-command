package br.com.leandroferreira.dagcommand.adapter

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.enums.ModuleType
import br.com.leandroferreira.dagcommand.enums.OutputType
import br.com.leandroferreira.dagcommand.extension.CommandExtension

fun CommandExtension.parse() : Config = Config(findFilter(filter), defaultBranch, findOutputType(outputType))

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
