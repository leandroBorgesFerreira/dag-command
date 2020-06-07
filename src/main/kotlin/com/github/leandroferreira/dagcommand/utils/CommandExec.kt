package com.github.leandroferreira.dagcommand.utils

import java.util.concurrent.TimeUnit
import kotlin.streams.toList

object CommandExec : CommandExecutor {
    override fun runCommand(command: String): List<String> =
        Runtime.getRuntime()
            .exec(command)
            .apply { waitFor(10, TimeUnit.SECONDS) }
            .inputStream
            .bufferedReader()
            .lines()
            .toList()
}
