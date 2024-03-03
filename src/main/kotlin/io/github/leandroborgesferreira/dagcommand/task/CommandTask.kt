package io.github.leandroborgesferreira.dagcommand.task

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import io.github.leandroborgesferreira.dagcommand.logic.affectedModules
import io.github.leandroborgesferreira.dagcommand.logic.changedModules
import io.github.leandroborgesferreira.dagcommand.logic.createEdgeList
import io.github.leandroborgesferreira.dagcommand.logic.generalInformation
import io.github.leandroborgesferreira.dagcommand.logic.groupByStages
import io.github.leandroborgesferreira.dagcommand.logic.nodesData
import io.github.leandroborgesferreira.dagcommand.logic.parseAdjacencyList
import io.github.leandroborgesferreira.dagcommand.output.addHeader
import io.github.leandroborgesferreira.dagcommand.output.commandWithFeedback
import io.github.leandroborgesferreira.dagcommand.output.createPrintFn
import io.github.leandroborgesferreira.dagcommand.output.printConfig
import io.github.leandroborgesferreira.dagcommand.output.printGraphInfo
import io.github.leandroborgesferreira.dagcommand.utils.CommandExec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

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

        val printFn = createPrintFn(config.outputType, buildPath(), OUTPUT_DIRECTORY_NAME)
        val adjacencyList: AdjacencyList = parseAdjacencyList(project, config)

        if (config.printModulesInfo) {
            commandWithFeedback("Writing adjacency list...") {
                printFn(OUTPUT_GRAPH, adjacencyList)
            }

            val edgeList = createEdgeList(adjacencyList)
            commandWithFeedback("Writing edges list...") {
                printFn(OUTPUT_EDGE_LIST, edgeList)
            }

            val nodeList = nodesData(adjacencyList)
            val buildStages = nodeList.groupByStages()
            commandWithFeedback("Build stages...") {
                printFn(OUTPUT_NODE_LIST, nodeList)
                printFn(BUILD_STAGES, buildStages)
            }

            generalInformation(
                adjacencyList,
                nodeList = nodeList,
                edgeList = edgeList
            ).let(::printGraphInfo)
        }

        val changedModules: List<String> =
            changedModules(CommandExec, config.defaultBranch, adjacencyList).also { modules ->
                println("\nChanged modules: ${modules.joinToString()}")
            }

        val affectedModules: Collection<String> =
            affectedModules(adjacencyList, changedModules).also { modules ->
                println("\nAffected modules: ${modules.joinToString()}")
            }.let { modules ->
                if (config.outputType == OutputType.CSV) {
                    modules.addHeader("Module")
                } else {
                    modules
                }
            }

        printFn(OUTPUT_FILE_NAME_AFFECTED, affectedModules)
    }

    private fun buildPath() = config.outputPath ?: project.layout.buildDirectory.asFile.get().path
}
