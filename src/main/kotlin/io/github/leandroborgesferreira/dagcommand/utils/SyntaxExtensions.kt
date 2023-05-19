package io.github.leandroborgesferreira.dagcommand.utils

import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider

inline fun <reified T> ExtensionContainer.create(name: String) =
    this.create(name, T::class.java)

inline fun <reified T : Task> TaskContainer.registerExt(
    name: String,
    configuration: Action<in T>
): TaskProvider<T> = this.register(name, T::class.java, configuration)
