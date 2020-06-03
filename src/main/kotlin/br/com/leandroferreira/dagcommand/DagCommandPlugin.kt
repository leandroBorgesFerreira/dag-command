package br.com.leandroferreira.dagcommand

import br.com.leandroferreira.dagcommand.adapter.parse
import br.com.leandroferreira.dagcommand.extension.CommandExtension
import br.com.leandroferreira.dagcommand.task.CommandTask
import br.com.leandroferreira.dagcommand.utils.create
import br.com.leandroferreira.dagcommand.utils.registerExt
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project

private const val CONFIGURE_CLOJURE_NAME = "dagCommand"
private const val CONFIGURE_COMMAND_NAME = "dag-command"

class DagCommandPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension: CommandExtension = project.extensions.create<CommandExtension>(CONFIGURE_CLOJURE_NAME)

        project.tasks.registerExt<CommandTask>(CONFIGURE_COMMAND_NAME, Action {
            it.config = extension.parse()
        })
    }
}
