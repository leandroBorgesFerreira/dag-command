package io.github.leandroborgesferreira.dagcommand.output

import java.io.BufferedWriter
import java.io.File

fun writeToFile(directory: File, fileName: String, content: Iterable<String>) {
    createFileBuffer(directory, fileName).use { writer ->
        content.forEach { line ->
            writer.write(line)
            writer.newLine()
        }
    }
}

fun writeToFile(directory: File, fileName: String, content: String) {
    createFileBuffer(directory, fileName).use { writer ->
        writer.write(content)
    }
}

private fun createFileBuffer(directory: File, fileName: String): BufferedWriter {
    if (!directory.exists()) directory.mkdirs()

    return File(directory.path, fileName)
        .outputStream()
        .bufferedWriter()
}
