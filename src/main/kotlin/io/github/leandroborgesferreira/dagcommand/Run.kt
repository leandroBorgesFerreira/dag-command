package io.github.leandroborgesferreira.dagcommand

import io.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import io.github.leandroborgesferreira.dagcommand.domain.DagProject
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
import io.github.leandroborgesferreira.dagcommand.output.printGraphInfo
import io.github.leandroborgesferreira.dagcommand.utils.CommandExec

private const val OUTPUT_DIRECTORY_NAME = "dag-command"
private const val OUTPUT_FILE_NAME_AFFECTED = "affected-modules"
private const val OUTPUT_GRAPH = "adjacencies-list"
private const val OUTPUT_EDGE_LIST = "edge-list"
private const val OUTPUT_NODE_LIST = "node-list"
private const val BUILD_STAGES = "build-stages"

fun runDagCommand(
    project: List<DagProject>,
    outputType: OutputType,
    defaultBranch: String,
    printModulesInfo: Boolean,
    buildPath: String,
) {
    val printFn = createPrintFn(outputType, buildPath, OUTPUT_DIRECTORY_NAME)

    val adjacencyList: AdjacencyList = parseAdjacencyList(project)

    if (printModulesInfo) {
        commandWithFeedback("Writing adjacency list...") {
            printFn(OUTPUT_GRAPH, adjacencyList)
        }

        val edgeList = commandWithFeedback("Writing edges list...") {
            createEdgeList(adjacencyList).also {
                printFn(OUTPUT_EDGE_LIST, createEdgeList(adjacencyList))
            }
        }

        val nodeList = commandWithFeedback("Build stages...") {
            val nodeList = nodesData(adjacencyList)

            printFn(OUTPUT_NODE_LIST, nodeList)
            printFn(BUILD_STAGES, nodeList.groupByStages())

            nodeList
        }

        generalInformation(
            adjacencyList,
            nodeList = nodeList,
            edgeList = edgeList
        ).let(::printGraphInfo)
    }

    val changedModules: List<String> =
        changedModules(CommandExec, defaultBranch, adjacencyList).also { modules ->
            println("\nChanged modules: ${modules.joinToString()}")
        }

    val affectedModules: Collection<String> =
        affectedModules(adjacencyList, changedModules).also { modules ->
            println("\nAffected modules: ${modules.joinToString()}")
        }.let { modules ->
            if (outputType == OutputType.CSV) {
                modules.addHeader("Module")
            } else {
                modules
            }
        }

    printFn(OUTPUT_FILE_NAME_AFFECTED, affectedModules)
}
