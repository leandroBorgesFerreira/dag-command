package com.github.leandroborgesferreira.dagcommand.extension

open class CommandExtension(
    var filter: String = "No name",
    var defaultBranch: String = "master",
    var outputType: String = "console",
    var outputPath: String? = null,
    var printAdjacencyList: Boolean = true,
    var printEdgesList: Boolean = true,
    var printNodesList: Boolean = true
)
