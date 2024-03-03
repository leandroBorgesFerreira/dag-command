package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.utils.CommandExecutor
import io.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
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

private fun expectedModules(): List<String> = listOf(":A", ":B")
