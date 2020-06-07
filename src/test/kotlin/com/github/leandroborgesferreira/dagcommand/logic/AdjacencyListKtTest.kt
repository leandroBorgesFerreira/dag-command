package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import org.junit.Test
import kotlin.test.assertEquals

class AdjacencyListKtTest {

    @Test
    fun `proves that graph is reverted correctly`() {
        assertEquals(simpleRevertedGraph(), revertAdjacencyList(simpleGraph()))
    }
}

private fun simpleRevertedGraph() =
    mapOf(
        "A" to setOf(),
        "B" to setOf("A"),
        "C" to setOf("A", "B"),
        "D" to setOf("A"),
        "F" to setOf("B", "C", "D")
    )
