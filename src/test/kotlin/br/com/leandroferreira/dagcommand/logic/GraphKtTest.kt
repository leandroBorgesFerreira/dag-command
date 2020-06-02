package br.com.leandroferreira.dagcommand.logic

import br.com.leandroferreira.dagcommand.utils.disconnectedGraph
import br.com.leandroferreira.dagcommand.utils.simpleGraph
import org.junit.Test
import kotlin.test.assertEquals

class GraphKtTest {

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
    fun `proves that implementation works for completly disconnected graph`() {
        val resultSet = affectedModules(disconnectedGraph(), listOf("A", "B", "C", "D", "E", "F"))

        assertEquals(setOf("A", "B", "C", "D", "E", "F"), resultSet)
    }
}
