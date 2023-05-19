package io.github.leandroborgesferreira.dagcommand

import io.github.leandroborgesferreira.dagcommand.adapter.parse
import io.github.leandroborgesferreira.dagcommand.extension.CommandExtension
import io.github.leandroborgesferreira.dagcommand.task.CommandTask
import io.github.leandroborgesferreira.dagcommand.utils.create
import io.github.leandroborgesferreira.dagcommand.utils.registerExt
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

private const val CONFIGURE_CLOJURE_NAME = "dagCommand"
private const val CONFIGURE_COMMAND_NAME = "dag-command"

class DagCommandPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension: CommandExtension = project.extensions.create(CONFIGURE_CLOJURE_NAME)

        project.tasks.registerExt<CommandTask>(CONFIGURE_COMMAND_NAME, Action {
            it.config = extension.parse()
        })
    }
}
