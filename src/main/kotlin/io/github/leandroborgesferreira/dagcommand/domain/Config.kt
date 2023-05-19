package io.github.leandroborgesferreira.dagcommand.domain

import io.github.leandroborgesferreira.dagcommand.enums.ModuleType
import io.github.leandroborgesferreira.dagcommand.enums.OutputType

data class Config(
    val filter: ModuleType,
    val defaultBranch: String,
    val outputType: OutputType,
    val outputPath: String?,
    val printModulesInfo: Boolean
)
