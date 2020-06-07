package com.github.leandroferreira.dagcommand.utils

interface CommandExecutor {
    fun runCommand(command: String): List<String>
}
