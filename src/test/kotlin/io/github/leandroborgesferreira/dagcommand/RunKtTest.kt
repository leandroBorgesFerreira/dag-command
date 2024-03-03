package io.github.leandroborgesferreira.dagcommand

import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import io.github.leandroborgesferreira.dagcommand.utils.testProject
import org.junit.Test

private const val TEST_BUILD_PATH = "./test-results"

class RunKtTest {

    @Test
    fun `runDagCommand command with empty project should not throw exceptions`() {
        runDagCommand(
            project = listOf(),
            outputType = OutputType.JSON_PRETTY,
            defaultBranch = "main",
            printModulesInfo = true,
            buildPath = TEST_BUILD_PATH
        )
    }

    @Test
    fun `runDagCommand command should run correctly with project`() {
        runDagCommand(
            project = testProject(),
            outputType = OutputType.JSON_PRETTY,
            defaultBranch = "main",
            printModulesInfo = true,
            buildPath = TEST_BUILD_PATH
        )
    }

}
