package br.com.leandroferreira.dagcommand.output

import br.com.leandroferreira.dagcommand.logic.AdjacencyList
import com.google.gson.Gson
import java.io.File

fun writeToFile(directory: File, fileName: String, content: Iterable<String>) {
    createFile(directory, fileName)
        .outputStream()
        .bufferedWriter()
        .use { writer ->
            println("Printing affected modules...")
            content.forEach { line ->
                println("Printing: $line")
                writer.write(line)
                writer.newLine()
            }
            println("Done")
        }
}

fun writeToFile(directory: File, fileName: String, content: String) {
    createFile(directory, fileName)
        .outputStream()
        .bufferedWriter()
        .use { writer ->
            writer.write(content)
        }
}

private fun createFile(directory: File, fileName: String): File {
    if (!directory.exists()) directory.mkdirs()

    return File(directory.path, fileName)
}
