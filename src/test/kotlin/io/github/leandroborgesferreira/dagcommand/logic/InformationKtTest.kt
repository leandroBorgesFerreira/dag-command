package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.domain.GraphInformation
import io.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import org.junit.Test
import kotlin.test.assertEquals

internal class InformationKtTest {

    @Test
    fun `proves that graph information if correctly generated`() {
        val information = generalInformation(simpleGraph())

        assertEquals(
            GraphInformation(
                nodeCount = 6,
                edgeCount = 10,
                buildStages = 4,
                buildCoefficient = 17.0/6.0
            ),
            information
        )
    }
}
