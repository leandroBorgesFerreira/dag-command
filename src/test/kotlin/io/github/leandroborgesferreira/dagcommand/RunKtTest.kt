package io.github.leandroborgesferreira.dagcommand

import io.github.leandroborgesferreira.dagcommand.enums.OutputType
import io.github.leandroborgesferreira.dagcommand.utils.testProject
import org.junit.Test
import java.io.File
import kotlin.test.assertContentEquals

private const val TEST_BUILD_PATH = "./test-results"

class RunKtTest {

    @Test
    fun `runDagCommand command with empty project should not throw exceptions`() {
        runDagCommand(
            project = listOf(),
            outputType = OutputType.JSON_PRETTY,
            defaultBranch = "main",
            printModulesInfo = true,
            buildPath = TEST_BUILD_PATH,
            excludeIntermediateModules = true,
        )
    }

    @Test
    fun `runDagCommand command should run correctly with project`() {
        runDagCommand(
            project = testProject(),
            outputType = OutputType.JSON_PRETTY,
            defaultBranch = "main",
            printModulesInfo = true,
            buildPath = TEST_BUILD_PATH,
            excludeIntermediateModules = true,
        )

        val checkFilesMap = getFilenames("$TEST_BUILD_PATH/check")
        val resultFilesMap = getFilenames("$TEST_BUILD_PATH/dag-command")

        checkFilesMap.forEach { (name, checkFile) ->
            val resultFile = resultFilesMap[name]!!
            assertContentEquals(checkFile.readLines(), resultFile.readLines(), "Files $name are not equal")
        }
    }
}

fun getFilenames(directoryPath: String): Map<String, File> =
    File(directoryPath).walk()
        .filter { file -> file.isFile }
        .map { file -> file.name to file }
        .toMap()
