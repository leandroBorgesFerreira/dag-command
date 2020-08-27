package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.utils.simpleEdgeList
import org.junit.Test
import kotlin.test.assertEquals

internal class InstabilityKtTest{

    @Test
    fun `proves that instability is correctly created`() {
        val edges = simpleEdgeList()

        assertEquals(0.0, calculateInstability("A", edges, 6))
        assertEquals(1.0/6.0, calculateInstability("B", edges, 6))
        assertEquals(2.0/6.0, calculateInstability("C", edges, 6))
        assertEquals(1.0/6.0, calculateInstability("D", edges, 6))
        assertEquals(2.0/6.0, calculateInstability("E", edges, 6))
        assertEquals(4.0/6.0, calculateInstability("F", edges, 6))
    }
}
