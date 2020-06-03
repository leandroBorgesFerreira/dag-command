package br.com.leandroferreira.dagcommand.extension

import br.com.leandroferreira.dagcommand.enums.OutputType

open class CommandExtension(
    var filter: String = "No name",
    var defaultBranch: String = "master",
    var outputType: String = "console"
)
