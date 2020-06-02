package br.com.leandroferreira.dagcommand.task

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.logic.*
import br.com.leandroferreira.dagcommand.output.writeToFile
import br.com.leandroferreira.dagcommand.utils.CommandExec
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
                File(project.buildDir.path, OUTPUT_DIRECTORY_NAME),
                OUTPUT_FILE_NAME_GRAPH,
                Gson().toJson(adjacencyList)
            )
        }

        val affectedModules = affectedModules(adjacencyList, changedModules(CommandExec))

        commandWithFeedback("Writing affected modules list... ") {
            writeToFile(File(project.buildDir.path, OUTPUT_DIRECTORY_NAME), OUTPUT_FILE_NAME_AFFECTED, affectedModules)
        }
    }
}

private fun printConfig(project: Project, config: Config) {
    println("--- Config ---")
    println("Filter: ${config.filter.value}")
    println("Output path: ${project.buildDir.path}")
    println("--------------\n")
}

private fun commandWithFeedback(message: String, func: () -> Unit) {
    print(message)
    func()
    print("Done\n")
}
