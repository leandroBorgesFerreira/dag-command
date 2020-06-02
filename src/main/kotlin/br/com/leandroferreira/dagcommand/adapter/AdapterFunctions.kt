package br.com.leandroferreira.dagcommand.adapter

import br.com.leandroferreira.dagcommand.domain.Config
import br.com.leandroferreira.dagcommand.enums.ModuleType
import br.com.leandroferreira.dagcommand.extension.CommandExtension

fun CommandExtension.parse() : Config = Config(findFilter(filter))

private fun findFilter(filter: String) =
    ModuleType
        .values()
        .find { enum -> enum.value.equals(filter, ignoreCase = true) }
        ?: ModuleType.All
