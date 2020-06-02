package br.com.leandroferreira.dagcommand.task

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.logic.affectedModules
import br.com.leandroferreira.dagcommand.logic.changedModules
import br.com.leandroferreira.dagcommand.logic.parseAdjacencyList
import br.com.leandroferreira.dagcommand.utils.CommandExec
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class CommandTask : DefaultTask() {

    @Input
    lateinit var config: Config

    @TaskAction
    private fun command() {
        println("Filter: ${config.filter.value}")

        val affectedModules = affectedModules(parseAdjacencyList(project, config), changedModules(CommandExec))

        println("Affected modules: ")
        project.subprojects.filter { subproject -> affectedModules.contains(subproject.name) }
            .forEach {  subproject ->
                println(subproject.name)
            }
    }
}
