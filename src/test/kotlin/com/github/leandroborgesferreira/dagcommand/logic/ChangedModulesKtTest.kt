package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.utils.CommandExec
import com.github.leandroborgesferreira.dagcommand.utils.CommandExecutor
import com.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import kotlin.test.assertEquals

class ChangedModulesKtTest {

    private val changedModules = listOf(
        "4.4% A/src/androidTest/java/com/module1/",
        "7.3% A/src/main/java/com/module1/android/",
        "4.9% A/src/main/java/com/module1/features/feature1/",
        "3.4% A/src/main/java/com/module1/features/search/",
        "4.9% A/src/main/java/com/module1/features/information/",
        "5.8% A/src/main/java/com/module1/network/",
        "10.7% A/src/main/java/com/module1/",
        "3.4% A/src/test/java/com/module1/android/",
        "3.9% A/",
        "5.8% B/src/main/java/com/module1/inbox/messages/",
        "3.9% B/src/",
        "4.4% C/src/main/java/com/module1/network/responsemodels/",
        "4.9% D/src/main/java/com/module1/repositories/",
        "3.9% E/src/main/res/drawable/",
        "8.8% E/src/main/res/",
        "3.9% F/src/main/java/com/module1/favorites/",
        "1.9% Z/src/main/java/com/module1/favorites/",
        "2.9% W/src/main/java/com/module1/favorites/"
    )

    private val commandExecutor: CommandExecutor = mock {
        on(it.runCommand(any())) doReturn changedModules
    }

    @Test
    fun `proves that changes get parsed correctly`() {
        val expected = listOf("A", "B", "C", "D", "E", "F")

        assertEquals(expected, changedModules(commandExecutor, "master", simpleGraph()))
    }
}
