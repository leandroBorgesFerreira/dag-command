package io.github.leandroborgesferreira.dagcommand.extension

open class CommandExtension(
    var defaultBranch: String = "master",
    var outputType: String = "console",
    var outputPath: String? = null,
    var printModulesInfo: Boolean = true,
    var filterModules: String? = null,
    var verbose: Boolean = false
)
