package io.github.leandroborgesferreira.dagcommand.logic

import io.github.leandroborgesferreira.dagcommand.utils.simpleGraph
import org.hamcrest.CoreMatchers.anyOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import kotlin.test.assertEquals

class CompilePhaseKtTest {

    @Test
    fun `compile phases should be correctly calculated`() {
        val expected: List<Set<String>> = listOf(
            setOf(":A"), setOf(":B", ":D"), setOf(":C"), setOf(":E"), setOf(":F")
        )

        val result = compilePhases(simpleGraph())

        assertEquals(expected, result)
    }

    @Test
    fun `topological sort should work for simple graph`() {
        val expected1 = listOf(":A", ":B", ":D", ":C", ":E", ":F")
        val expected2 = listOf(":A", ":D", ":B", ":C", ":E", ":F")
        val result: List<String> = topologicalSort(simpleGraph())

        //It is possible to have 2 correct answers for the sort
        assertThat(result, anyOf(`is`(expected1), `is`(expected2)))
    }
}