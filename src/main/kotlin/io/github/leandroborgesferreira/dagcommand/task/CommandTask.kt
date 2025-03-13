package io.github.leandroborgesferreira.dagcommand.task

import io.github.leandroborgesferreira.dagcommand.domain.Config
import io.github.leandroborgesferreira.dagcommand.logic.toDagProjectList
import io.github.leandroborgesferreira.dagcommand.output.printConfig
import io.github.leandroborgesferreira.dagcommand.runDagCommand
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class CommandTask : DefaultTask() {

    @Input
    lateinit var config: Config

    @TaskAction
    fun command() {
        printConfig(project, config)

        runDagCommand(
            project = project.toDagProjectList(config.filterModules),
            outputType = config.outputType,
            defaultBranch = config.defaultBranch,
            printModulesInfo = config.printModulesInfo,
            buildPath = buildPath(),
        )
    }

    private fun buildPath() = config.outputPath ?: project.layout.buildDirectory.asFile.get().path
}
