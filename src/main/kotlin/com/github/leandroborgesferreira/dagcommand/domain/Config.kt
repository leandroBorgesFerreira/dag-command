package com.github.leandroborgesferreira.dagcommand.domain

import com.github.leandroborgesferreira.dagcommand.enums.ModuleType
import com.github.leandroborgesferreira.dagcommand.enums.OutputType

data class Config(
    val filter: ModuleType,
    val defaultBranch: String,
    val outputType: OutputType,
    val outputPath: String?,
    val printAdjacencyList: Boolean,
    val printEdgesList: Boolean,
    val printBuildStages: Boolean
)
