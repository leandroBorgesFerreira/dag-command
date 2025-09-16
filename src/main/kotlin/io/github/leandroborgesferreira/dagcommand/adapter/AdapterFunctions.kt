package io.github.leandroborgesferreira.dagcommand.adapter

import com.google.gson.reflect.TypeToken
import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import io.github.leandroborgesferreira.dagcommand.extension.CommandExtension
import io.github.leandroborgesferreira.dagcommand.output.getGson
import java.lang.reflect.Type

fun CommandExtension.parse(): Config =
    Config(
        defaultBranch = defaultBranch,
        outputType = findOutputType(outputType),
        outputPath = outputPath,
        printModulesInfo = printModulesInfo,
        filterModules = filterModules?.let(::parseFilter) ?: emptySet(),
        verbose = verbose
    )

private fun findOutputType(outputType: String): OutputType {
    val entries = OutputType.entries
    return OutputType.entries
        .find { enum -> enum.value.equals(outputType, ignoreCase = true) }
        ?: throw IllegalStateException(
            "This type of output: $outputType. Is not supported. Supported types are: ${entries.joinToString()}"
        )
}

private fun parseFilter(filterString: String): Set<String> {
    val typeOfT: Type = TypeToken.getParameterized(Set::class.java, String::class.java).type
    return getGson(false).fromJson(filterString, typeOfT)
}
