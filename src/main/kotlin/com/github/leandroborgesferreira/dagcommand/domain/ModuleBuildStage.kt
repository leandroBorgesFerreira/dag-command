package com.github.leandroborgesferreira.dagcommand.domain

data class ModuleBuildStage(
    val module: String,
    var stage: Int = 0
)
