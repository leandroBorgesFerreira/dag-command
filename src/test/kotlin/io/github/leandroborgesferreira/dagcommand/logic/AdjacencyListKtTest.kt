package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.utils.simpleGraph
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
        ":A" to setOf(),
        ":B" to setOf(":A"),
        ":C" to setOf(":A", ":B"),
        ":D" to setOf(":A"),
        ":E" to setOf(":C", ":D"),
        ":F" to setOf(":B", ":C", ":D", ":E")
    )
