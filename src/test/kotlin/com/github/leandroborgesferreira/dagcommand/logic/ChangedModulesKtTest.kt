package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.utils.CommandExec
import com.github.leandroborgesferreira.dagcommand.utils.CommandExecutor
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import kotlin.test.assertEquals

class ChangedModulesKtTest {

    private val changedModules = listOf(
        "4.4% module1/src/androidTest/java/com/module1/",
        "7.3% module1/src/main/java/com/module1/android/",
        "4.9% module1/src/main/java/com/module1/features/feature1/",
        "3.4% module1/src/main/java/com/module1/features/search/",
        "4.9% module1/src/main/java/com/module1/features/information/",
        "5.8% module1/src/main/java/com/module1/network/",
        "10.7% module1/src/main/java/com/module1/",
        "3.4% module1/src/test/java/com/module1/android/",
        "3.9% module1/",
        "5.8% inbox/src/main/java/com/module1/inbox/messages/",
        "3.9% inbox/src/",
        "4.4% network/src/main/java/com/module1/network/responsemodels/",
        "4.9% repositories/src/main/java/com/module1/repositories/",
        "3.9% resources/src/main/res/drawable/",
        "8.8% resources/src/main/res/",
        "3.9% favorites/src/main/java/com/module1/favorites/"
    )

    private val commandExecutor: CommandExecutor = mock {
        on(it.runCommand(any())) doReturn changedModules
    }

    @Test
    fun `proves that changes get parsed correctly`() {
        val expected = listOf<String>(
            "module1",
            "inbox",
            "network",
            "repositories",
            "resources",
            "favorites"
        )

        assertEquals(expected, changedModules(commandExecutor, "master"))
    }
}
