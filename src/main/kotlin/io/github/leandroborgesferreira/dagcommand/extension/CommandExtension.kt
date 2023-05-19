package io.github.leandroborgesferreira.dagcommand.extension

open class CommandExtension(
    var filter: String = "No name",
    var defaultBranch: String = "master",
    var outputType: String = "console",
    var outputPath: String? = null,
    var printModulesInfo: Boolean = true
)
