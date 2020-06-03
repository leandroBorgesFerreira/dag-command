package br.com.leandroferreira.dagcommand.logic

import br.com.leandroferreira.dagcommand.utils.CommandExecutor
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
        assertEquals(expectedModules(), changedModules(commandExecutor, "develop"))
    }
}

private fun commandExecList(): List<String> =
    listOf(
        "25.0% core/src/main/java/com/something/android/core/extensions/",
        "50.0% companyname/src/main/java/com/something/android/activities/fragments/blabla/"
    )

private fun expectedModules(): List<String> = listOf("core", "companyname")
