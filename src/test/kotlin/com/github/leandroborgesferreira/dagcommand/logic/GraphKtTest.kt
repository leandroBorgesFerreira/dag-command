package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.Node
import com.github.leandroborgesferreira.dagcommand.utils.disconnectedGraph
import com.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import org.junit.Test
import kotlin.test.assertEquals

class GraphKtTest {

    @Test
    fun `proves that affected modules do not include folders that are not modules`() {
        val resultSet = affectedModules(simpleGraph(), listOf("B", "C", "not", "in", "the", "graph"))

        assertEquals(setOf("B", "C", "E", "F"), resultSet)
    }

    @Test
    fun `proves that affected graph works for middle position change`() {
        val resultSet = affectedModules(simpleGraph(), listOf("B", "C"))

        assertEquals(setOf("B", "C", "E", "F"), resultSet)
    }

    @Test
    fun `proves that affected graph works for last position change`() {
        val resultSet = affectedModules(simpleGraph(), listOf("F"))

        assertEquals(setOf("F"), resultSet)
    }

    @Test
    fun `proves that affected graph works for first position change`() {
        val resultSet = affectedModules(simpleGraph(), listOf("A"))

        assertEquals(setOf("A", "B", "C", "D", "E", "F"), resultSet)
    }

    @Test
    fun `proves that implementation works for completely disconnected graph`() {
        val resultSet = affectedModules(disconnectedGraph(), listOf("A", "B", "C", "D", "E", "F"))

        assertEquals(setOf("A", "B", "C", "D", "E", "F"), resultSet)
    }

    @Test
    fun `proves that root nodes can be found`() {
        assertEquals(setOf("A"), findRootNodes(simpleGraph()))
        assertEquals(disconnectedGraph().keys, findRootNodes(disconnectedGraph()))
    }

    @Test
    fun `proves that build stage can be correctly found`() {
        val expected = listOf(
            Node("A", 0),
            Node("B", 1),
            Node("C", 2),
            Node("D", 1),
            Node("E", 3),
            Node("F", 4)
        )

        assertEquals(expected, nodeList(simpleGraph()))
    }
}
