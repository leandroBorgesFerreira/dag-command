package io.github.leandroborgesferreira.dagcommand.utils

interface CommandExecutor {
    fun runCommand(command: String): List<String>
}
