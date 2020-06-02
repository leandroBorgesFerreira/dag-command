package br.com.leandroferreira.dagcommand.utils

interface CommandExecutor {
    fun runCommand(command: String): List<String>
}
