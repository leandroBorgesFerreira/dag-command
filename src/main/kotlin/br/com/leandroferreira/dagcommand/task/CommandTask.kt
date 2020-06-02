package br.com.leandroferreira.dagcommand.task

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.logic.affectedModules
import br.com.leandroferreira.dagcommand.logic.changedModules
import br.com.leandroferreira.dagcommand.logic.parseAdjacencyList
import br.com.leandroferreira.dagcommand.output.writeToFile
import br.com.leandroferreira.dagcommand.utils.CommandExec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

private const val OUTPUT_DIRECTORY_NAME = "dag-command"
private const val OUTPUT_FILE_NAME = "affected-modules.txt"

open class CommandTask : DefaultTask() {

    @Input
    lateinit var config: Config

    @TaskAction
    private fun command() {
        println("Filter: ${config.filter.value}")
        println("Path: ${project.buildDir.path}")

        val affectedModules = affectedModules(parseAdjacencyList(project, config), changedModules(CommandExec))

        writeToFile(File(project.buildDir.path, OUTPUT_DIRECTORY_NAME), OUTPUT_FILE_NAME, affectedModules)
    }
}
