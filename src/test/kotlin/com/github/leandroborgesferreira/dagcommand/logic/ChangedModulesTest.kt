package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.utils.CommandExecutor
import com.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class ChangedModulesTest {

    private val commandExecutor: CommandExecutor = mock {
        on(it.runCommand(any())) doReturn commandExecList()
    }

    @Test
    fun `should parse git modules correctly`() {
        assertEquals(expectedModules(), changedModules(commandExecutor, "develop", simpleGraph()))
    }
}

private fun commandExecList(): List<String> =
    listOf(
        "25.0% A/src/main/java/com/something/android/core/extensions/",
        "50.0% B/src/main/java/com/something/android/activities/fragments/blabla/",
        "25.0% Z/src/main/java/com/something/android/activities/fragments/blabla/"
    )

private fun expectedModules(): List<String> = listOf("A", "B")
