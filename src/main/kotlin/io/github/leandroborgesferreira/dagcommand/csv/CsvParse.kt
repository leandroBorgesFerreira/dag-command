package io.github.leandroborgesferreira.dagcommand.csv

import io.github.leandroborgesferreira.dagcommand.domain.Edge
import io.github.leandroborgesferreira.dagcommand.domain.Node

fun List<Edge>.toCsvTable(): CsvTable {
    val header = listOf("Source", "Target", "Weight")

    val csvLines: List<CsvLine> = this.map { edge ->
        listOf(edge.source, edge.target, edge.weight.toString())
    }

    return CsvTable(header, csvLines)
}

fun List<Node>.toCsv(): CsvTable {
    val header = listOf("Id", "Label", "Build stage")

    val csvLines: List<CsvLine> = this.map { node ->
        listOf(node.name, node.name, node.buildStage.toString())
    }

    return CsvTable(header, csvLines)
}

fun CsvTable.toPrintableList() : List<String> {
    val csvHeader = header.joinToString(separator = ";")
    val contentLines = content.map { words -> words.joinToString(";") }

    return listOf(csvHeader) + contentLines
}

fun List<Edge>.toPrintableCsv() = this.toCsvTable().toPrintableList()

fun List<Node>.printableCsv() = this.toCsv().toPrintableList()


