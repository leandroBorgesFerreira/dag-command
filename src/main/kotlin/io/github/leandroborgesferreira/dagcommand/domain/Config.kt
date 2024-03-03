package io.github.leandroborgesferreira.dagcommand.domain

import io.github.leandroborgesferreira.dagcommand.enums.OutputType

data class Config(
    val defaultBranch: String,
    val outputType: OutputType,
    val outputPath: String?,
    val printModulesInfo: Boolean,
    val filterModules: Set<String>,
)
