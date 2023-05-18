package com.github.leandroborgesferreira.dagcommand.task

import com.github.leandroborgesferreira.dagcommand.csv.printableCsv
import com.github.leandroborgesferreira.dagcommand.csv.toPrintableCsv
import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.domain.Config
import com.github.leandroborgesferreira.dagcommand.domain.GraphInformation
import com.github.leandroborgesferreira.dagcommand.enums.OutputType
import com.github.leandroborgesferreira.dagcommand.logic.*
import com.github.leandroborgesferreira.dagcommand.output.writeToFile
import com.github.leandroborgesferreira.dagcommand.utils.CommandExec
import com.google.gson.Gson
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

open class CommandTask : DefaultTask() {

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
                    OutputType.CSV -> jsonOutput(OUTPUT_GRAPH, adjacencyList) //There's still not CSV support for adjacency list
                }
            }

            commandWithFeedback("Writing edges list...") {
                val edgeList = createEdgeList(adjacencyList)

                when (config.outputType) {
                    OutputType.JSON -> jsonOutput(OUTPUT_EDGE_LIST, edgeList)
                    OutputType.CSV -> csvOutput(OUTPUT_EDGE_LIST, edgeList.toPrintableCsv())
                }
            }

            if (config.printModulesInfo) {
                try {
                    commandWithFeedback("Build stages...") {
                        val nodeList = nodesData(adjacencyList)

                        when (config.outputType) {
                            OutputType.JSON -> jsonOutput(OUTPUT_NODE_LIST, nodeList)
                            OutputType.CSV -> csvOutput(OUTPUT_NODE_LIST, nodeList.printableCsv())
                        }
                    }

                    generalInformation(adjacencyList).let(::printGraphInfo)
                } catch (e: IllegalStateException) {
                    println("A problem happened when calculating the information about the modules, skipping it.")
                }
            }
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

    private fun jsonOutput(fileName: String, data: Any) {
        writeToFile(
            File(buildPath(), OUTPUT_DIRECTORY_NAME),
            "$fileName.json",
            Gson().toJson(data)
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
