package io.github.leandroborgesferreira.dagcommand.task

import io.github.leandroborgesferreira.dagcommand.csv.printableCsv
import io.github.leandroborgesferreira.dagcommand.csv.toPrintableCsv
import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.domain.GraphInformation
import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import io.github.leandroborgesferreira.dagcommand.logic.*
import io.github.leandroborgesferreira.dagcommand.output.writeToFile
import io.github.leandroborgesferreira.dagcommand.utils.CommandExec
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

private const val OUTPUT_DIRECTORY_NAME = "dag-command"
private const val OUTPUT_FILE_NAME_AFFECTED = "affected-modules"
private const val OUTPUT_GRAPH = "adjacencies-list"
private const val OUTPUT_EDGE_LIST = "edge-list"
private const val OUTPUT_NODE_LIST = "node-list"
private const val BUILD_STAGES = "build-stages"

abstract class CommandTask : DefaultTask() {

    @Input
    lateinit var config: Config

    @TaskAction
    private fun command() {
        printConfig(project, config)

        val adjacencyList: AdjacencyList = parseAdjacencyList(project, config)

        if (config.printModulesInfo) {
            commandWithFeedback("Writing adjacency list...") {
                when (config.outputType) {
                    OutputType.JSON -> jsonOutput(OUTPUT_GRAPH, adjacencyList)
                    OutputType.CSV -> jsonOutput(
                        OUTPUT_GRAPH,
                        adjacencyList
                    ) //There's still not CSV support for adjacency list
                }
            }

            val edgeList = createEdgeList(adjacencyList)
            commandWithFeedback("Writing edges list...") {
                when (config.outputType) {
                    OutputType.JSON -> jsonOutput(OUTPUT_EDGE_LIST, edgeList)
                    OutputType.CSV -> csvOutput(OUTPUT_EDGE_LIST, edgeList.toPrintableCsv())
                }
            }

            val nodeList = nodesData(adjacencyList)
            commandWithFeedback("Build stages...") {
                val buildStages = nodeList.groupByStages()

                when (config.outputType) {
                    OutputType.JSON -> jsonOutput(OUTPUT_NODE_LIST, nodeList)
                    OutputType.CSV -> csvOutput(OUTPUT_NODE_LIST, nodeList.printableCsv())
                }

                when (config.outputType) {
                    OutputType.JSON -> jsonOutput(BUILD_STAGES, buildStages, prettyPrint = true)
                    OutputType.CSV -> jsonOutput(BUILD_STAGES, buildStages, prettyPrint = true)
                }
            }

            generalInformation(adjacencyList, nodeList = nodeList, edgeList = edgeList).let(::printGraphInfo)
        }

        val changedModules: List<String> =
            changedModules(CommandExec, config.defaultBranch, adjacencyList).also { modules ->
                println("Changed modules: ${modules.joinToString()}\n")
            }

        val affectedModules: Set<String> = affectedModules(adjacencyList, changedModules).also { modules ->
            println("Affected modules: ${modules.joinToString()}\n")
        }

        when (config.outputType) {
            OutputType.JSON -> jsonOutput(OUTPUT_FILE_NAME_AFFECTED, affectedModules)
            OutputType.CSV -> csvOutput(OUTPUT_FILE_NAME_AFFECTED, affectedModules.addHeader("Module"))
        }
    }

    private fun buildPath() = config.outputPath ?: project.buildDir.path

    private fun jsonOutput(fileName: String, data: Any, prettyPrint: Boolean = false) {
        writeToFile(
            File(buildPath(), OUTPUT_DIRECTORY_NAME),
            "$fileName.json",
            getGson(prettyPrint).toJson(data)
        )
    }

    private fun csvOutput(fileName: String, lines: Iterable<String>) {
        writeToFile(
            File(buildPath(), OUTPUT_DIRECTORY_NAME),
            "$fileName.csv",
            lines
        )
    }
}

private fun getGson(prettyPrint: Boolean) = GsonBuilder()
    .apply {
        if (prettyPrint) {
            setPrettyPrinting()
        }
    }.create()

private fun printConfig(project: Project, config: Config) {
    println("--- Config ---")
    println("Filter: ${config.filter.value}")
    println("Default branch: ${config.defaultBranch}")
    println("Output type: ${config.outputType.value}")
    println("Output path: ${project.buildDir.path}")
    println("--------------\n")
}

private fun commandWithFeedback(message: String, func: () -> Unit) {
    print(message)
    func()
    print(" Done\n\n")
}

private fun printGraphInfo(information: GraphInformation) {
    println("Graph information:")
    println("Nodes count: ${information.nodeCount}")
    println("Edges count: ${information.edgeCount}")
    println("Build stages: ${information.buildStages}")
    println("Build coefficient: ${information.buildCoefficient}")
}

fun Iterable<String>.addHeader(header: String) = listOf(header) + this