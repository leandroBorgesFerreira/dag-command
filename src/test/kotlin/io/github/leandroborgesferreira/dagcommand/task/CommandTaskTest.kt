package io.github.leandroborgesferreira.dagcommand.task

import io.github.leandroborgesferreira.dagcommand.DagCommandPlugin
import io.github.leandroborgesferreira.dagcommand.extension.CommandExtension
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CommandTaskTest {

    @Test
    fun givenProjectStructure_whenTaskRuns_shouldUseGradleProjectPath() {
        val root = rootProjectWithNesting()
        DagCommandPlugin().apply(root)

        root.extensions.configure(CommandExtension::class.java) {
            it.outputType = "JSON"
        }
        val dagTask = root.tasks.withType(CommandTask::class.java).firstOrNull()
        assertNotNull(dagTask)
        dagTask.command()

        val expectedAdjacenciesList = """{":plain":[],":parentOfOthers:nested-A":[],":parentOfOthers:nested-B":[]}"""
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
