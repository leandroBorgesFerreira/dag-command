package com.github.leandroferreira.dagcommand.domain

import com.github.leandroferreira.dagcommand.enums.ModuleType
import com.github.leandroferreira.dagcommand.enums.OutputType

data class Config(
    val filter: ModuleType,
    val defaultBranch: String,
    val outputType: OutputType
)
