package br.com.leandroferreira.dagcommand.output

import java.io.File

fun writeToFile(directory: File, fileName: String, content: Iterable<String>) {
    val file = File(directory.path, fileName)

    if (!directory.exists()) directory.mkdirs()

    file.outputStream()
        .bufferedWriter()
        .use { writer ->
            content.forEach { line ->
                println("Printing: $line")
                writer.write(line)
                writer.newLine()
            }
        }
}
