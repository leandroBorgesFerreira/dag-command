package br.com.leandroferreira.dagcommand.task

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.logic.parseAdjacencyList
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class CommandTask : DefaultTask() {

    @Input
    lateinit var config: Config

    @TaskAction
    private fun command() {
        println("Filter: ${config.filter.value}")

        val list = parseAdjacencyList(project, config)

        list.forEach { (name, dependenciesName) ->
            val dependencies = dependenciesName.joinToString(separator = ", ")

            println("--- Module: $name ---")
            println("Dependencies: $dependencies")
        }

        println("The script has ended")
    }
}
