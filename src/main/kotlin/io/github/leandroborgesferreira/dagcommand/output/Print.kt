package io.github.leandroborgesferreira.dagcommand.output

import com.google.gson.GsonBuilder
import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.domain.GraphInformation
import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import org.gradle.api.Project
import java.io.File

fun createPrintFn(
    outputType: OutputType,
    buildPath: String,
    directoryName: String
): (String, Any) -> Unit = { fileName, data ->
    printFn(outputType, buildPath, directoryName, fileName, data)
}

private fun printFn(
    outputType: OutputType,
    buildPath: String,
    directoryName: String,
    fileName: String,
    data: Any
) {
    when (outputType) {
        OutputType.JSON -> jsonOutput(buildPath, directoryName, fileName, data, false)
        OutputType.JSON_PRETTY -> jsonOutput(buildPath, directoryName, fileName, data, true)
        // There's still not CSV support for adjacency list
        OutputType.CSV -> {
            if (data is Iterable<*>) {
                csvOutput(buildPath, directoryName, fileName, data)
            } else {
                println("Error when printing output as CSV, please try another output type")
            }
        }
    }
}

private fun jsonOutput(
    buildPath: String,
    filePath: String,
    fileName: String,
    data: Any,
    prettyPrint: Boolean
) {
    writeToFile(
        File(buildPath, filePath),
        "$fileName.json",
        getGson(prettyPrint).toJson(data)
    )
}

private fun csvOutput(buildPath: String, filePath: String, fileName: String, lines: Iterable<*>) {
    writeToFile(
        File(buildPath, filePath),
        "$fileName.csv",
        lines
    )
}

internal fun printConfig(project: Project, config: Config) {
    println("--- Config ---")
    println("Filter: ${config.filter.value}")
    println("Default branch: ${config.defaultBranch}")
    println("Output type: ${config.outputType.value}")
    println("Output path: ${project.layout.buildDirectory.asFile.get().path}")
    println("--------------\n")
}

internal fun commandWithFeedback(message: String, func: () -> Unit) {
    print(message)
    func()
    print(" Done\n\n")
}

internal fun printGraphInfo(information: GraphInformation) {
    println("Graph information:")
    println("Nodes count: ${information.nodeCount}")
    println("Edges count: ${information.edgeCount}")
    println("Build stages: ${information.buildStages}")
    println("Build coefficient: ${information.buildCoefficient}")
}

private fun getGson(prettyPrint: Boolean) = GsonBuilder()
    .apply {
        if (prettyPrint) {
            setPrettyPrinting()
        }
    }.create()

internal fun Iterable<String>.addHeader(header: String) = listOf(header) + this
