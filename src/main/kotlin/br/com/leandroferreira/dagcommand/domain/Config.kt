package br.com.leandroferreira.dagcommand.domain

import br.com.leandroferreira.dagcommand.enums.ModuleType
import br.com.leandroferreira.dagcommand.enums.OutputType

data class Config(
    val filter: ModuleType,
    val defaultBranch: String,
    val outputType: OutputType
)
