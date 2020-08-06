package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.github.leandroborgesferreira.dagcommand.utils.simpleDataFrame
import com.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import org.junit.Test
import kotlin.test.assertEquals

class DataFrameKtTest {

    @Test
    fun `proves that data frame is created correctly`() {
        val adjacencyList: AdjacencyList = simpleGraph()

        assertEquals(simpleDataFrame(), createDataFrame(adjacencyList))
    }
}
