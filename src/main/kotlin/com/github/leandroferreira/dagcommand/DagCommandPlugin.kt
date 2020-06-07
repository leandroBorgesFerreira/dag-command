package com.github.leandroferreira.dagcommand

import com.github.leandroferreira.dagcommand.adapter.parse
import com.github.leandroferreira.dagcommand.extension.CommandExtension
import com.github.leandroferreira.dagcommand.task.CommandTask
import com.github.leandroferreira.dagcommand.utils.create
import com.github.leandroferreira.dagcommand.utils.registerExt
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
