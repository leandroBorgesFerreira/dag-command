package io.github.leandroborgesferreira.dagcommand.task

import io.github.leandroborgesferreira.dagcommand.DagCommandPlugin
import io.github.leandroborgesferreira.dagcommand.extension.CommandExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CommandTaskTest {

    @Test
    fun givenShouldUseFullProjectPathName_thenOutputsUseFullName() {
        testAndAssertAdjacenciesListForConfiguration(
            root = rootProjectWithNesting(),
            expectedAdjacenciesList = """{":parentOfOthers":[],":plain":[],":parentOfOthers:nested-A":[],":parentOfOthers:nested-B":[]}"""
        ) {
            it.outputType = "JSON"
            it.useProjectPathAsOutputName = true
        }
    }

    @Test
    fun givenShouldNotUseFullProjectPathName_thenOutputsDoNotUseFullName() {
        testAndAssertAdjacenciesListForConfiguration(
            root = rootProjectWithNesting(),
            expectedAdjacenciesList = """{"parentOfOthers":[],"plain":[],"nested-A":[],"nested-B":[]}"""
        ) {
            it.outputType = "JSON"
            it.useProjectPathAsOutputName = false
        }
    }

    @Test
    fun givenShouldUseProjectPathAndShouldExcludeIntermediateModules_thenExcludesIntermediateModules() {
        testAndAssertAdjacenciesListForConfiguration(
            root = rootProjectWithNesting(),
            expectedAdjacenciesList = """{":plain":[],":parentOfOthers:nested-A":[],":parentOfOthers:nested-B":[]}"""
        ) {
            it.outputType = "JSON"
            it.useProjectPathAsOutputName = true
            it.excludeIntermediateModules = true
        }
    }

    @Test
    fun givenShouldNotUseProjectPathAndShouldExcludeIntermediateModules_thenExcludesIntermediateModules() {
        testAndAssertAdjacenciesListForConfiguration(
            root = rootProjectWithNesting(),
            expectedAdjacenciesList = """{"plain":[],"nested-A":[],"nested-B":[]}"""
        ) {
            it.outputType = "JSON"
            it.useProjectPathAsOutputName = false
            it.excludeIntermediateModules = true
        }
    }

    private fun testAndAssertAdjacenciesListForConfiguration(
        root: Project,
        expectedAdjacenciesList: String,
        extensionConfiguration: Action<in CommandExtension>,
    ) {
        DagCommandPlugin().apply(root)

        root.extensions.configure(CommandExtension::class.java, extensionConfiguration)
        val dagTask = root.tasks.withType(CommandTask::class.java).firstOrNull()
        assertNotNull(dagTask)
        dagTask.command()

        val adjacenciesListText = File(root.dagOutputDir(), "adjacencies-list.json").readText()
        assertEquals(expectedAdjacenciesList, adjacenciesListText)
    }

    private fun Project.dagOutputDir() = File(layout.buildDirectory.asFile.get(), "dag-command")

    private companion object {

        fun rootProjectWithNesting(): Project {
            val root = ProjectBuilder.builder()
                .withName("root")
                .build()

            val parentSubproject = ProjectBuilder.builder()
                .withName("parentOfOthers")
                .withParent(root)
                .build()

            ProjectBuilder.builder()
                .withName("nested-A")
                .withParent(parentSubproject)
                .build()

            ProjectBuilder.builder()
                .withName("nested-B")
                .withParent(parentSubproject)
                .build()

            ProjectBuilder.builder()
                .withName("plain")
                .withParent(root)
                .build()

            return root
        }
    }
}
