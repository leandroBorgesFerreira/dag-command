package io.github.leandroborgesferreira.dagcommand.csv

typealias CsvLine = List<String>

data class CsvTable(val header: List<String>, val content: List<CsvLine>)


