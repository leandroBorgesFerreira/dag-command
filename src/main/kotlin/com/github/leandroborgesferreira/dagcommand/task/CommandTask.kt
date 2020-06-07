package com.github.leandroborgesferreira.dagcommand.task

import com.github.leandroborgesferreira.dagcommand.domain.Config
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
private const val OUTPUT_FILE_NAME_AFFECTED = "affected-modules.txt"
private const val OUTPUT_FILE_NAME_GRAPH = "adjacencies-list.json"

open class CommandTask : DefaultTask() {

    @Input
    lateinit var config: Config

    @TaskAction
    private fun command() {
        printConfig(project, config)

        val adjacencyList: AdjacencyList = parseAdjacencyList(project, config)

        commandWithFeedback("Writing adjacency list... ") {
            writeToFile(
                File(buildPath(), OUTPUT_DIRECTORY_NAME),
                OUTPUT_FILE_NAME_GRAPH,
                Gson().toJson(adjacencyList)
            )
        }

        val changedModules: List<String> = changedModules(CommandExec, config.defaultBranch).also { modules ->
            println("Changed modules: ${modules.joinToString()}")
        }

        val affectedModules: Set<String> = affectedModules(adjacencyList, changedModules)

        when (config.outputType) {
            OutputType.CONSOLE -> printAffectedGraph(affectedModules)
            OutputType.FILE -> commandWithFeedback("Writing affected modules list... ") {
                writeToFile(File(buildPath(), OUTPUT_DIRECTORY_NAME), OUTPUT_FILE_NAME_AFFECTED, affectedModules)
            }
        }
    }

    private fun buildPath() = project.buildDir.path
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
    print("Done\n\n")
}

private fun printAffectedGraph(set: Set<String>) {
    println("Affected modules: ${set.joinToString()}")
}
