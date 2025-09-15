package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.Node
import io.github.leandroborgesferreira.dagcommand.domain.exception.CycleDetectedException
import io.github.leandroborgesferreira.dagcommand.utils.cyclicalObjectGraph
import io.github.leandroborgesferreira.dagcommand.utils.disconnectedGraph
import io.github.leandroborgesferreira.dagcommand.utils.graphWithCycle
import io.github.leandroborgesferreira.dagcommand.utils.objectGraph
import io.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import org.junit.Test
import kotlin.test.assertEquals

class GraphKtTest {

    @Test
    fun `proves that affected graph works for middle position change`() {
        val resultSet = affectedModules(simpleGraph(), listOf(":B", ":C"))

        assertEquals(setOf(":B", ":C", ":E", ":F"), resultSet)
    }

    @Test
    fun `proves that affected graph works for last position change`() {
        val resultSet = affectedModules(simpleGraph(), listOf(":F"))

        assertEquals(setOf(":F"), resultSet)
    }

    @Test
    fun `proves that affected graph works for first position change`() {
        val resultSet = affectedModules(simpleGraph(), listOf(":A"))

        assertEquals(setOf(":A", ":B", ":C", ":D", ":E", ":F"), resultSet)
    }

    @Test
    fun `proves that implementation works for completely disconnected graph`() {
        val resultSet =
            affectedModules(disconnectedGraph(), listOf(":A", ":B", ":C", ":D", ":E", ":F"))

        assertEquals(setOf(":A", ":B", ":C", ":D", ":E", ":F"), resultSet)
    }

    @Test
    fun `proves that root nodes can be found`() {
        assertEquals(setOf(":A"), findRootNodes(simpleGraph()))
        assertEquals(disconnectedGraph().keys, findRootNodes(disconnectedGraph()))
    }

    @Test
    fun `proves that build stage can be correctly found`() {
        val expected = listOf(
            Node(":A", 0, 0.0),
            Node(":B", 1, 1.0 / 6.0),
            Node(":C", 2, 2.0 / 6.0),
            Node(":D", 1, 1.0 / 6.0),
            Node(":E", 3, 2.0 / 6.0),
            Node(":F", 4, 4.0 / 6.0)
        )

        val result = nodesData(simpleGraph())

        assertEquals(expected, result)
    }

    @Test(expected = CycleDetectedException::class)
    fun `proves that cycles are detected`() {
        affectedModules(graphWithCycle(), listOf(":A", ":B", ":C"))
    }

    @Test(expected = CycleDetectedException::class)
    fun `cycles are detected when building DagProject`() {
        cyclicalObjectGraph()
            .iterableToDagProjectT(
                filterModules = emptySet(),
                visitedT = emptySet(),
                getNext = { project -> project.next },
                getPath = { project -> project.path },
            )
    }

    @Test
    fun `iterableToDagProjectT should work`() {
        objectGraph()
            .iterableToDagProjectT(
                filterModules = emptySet(),
                visitedT = emptySet(),
                getNext = { project -> project.next },
                getPath = { project -> project.path },
            )
    }
}

