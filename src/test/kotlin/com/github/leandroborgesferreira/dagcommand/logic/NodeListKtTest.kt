package com.github.leandroborgesferreira.dagcommand.logic

import com.github.leandroborgesferreira.dagcommand.domain.AdjacencyList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Test
import java.io.InputStreamReader


class NodeListKtTest {


    @Test
    fun `it should be possible to parse a complex adjacency list into into a node list`() {
        val gson = Gson()
        val jsonFile = this.javaClass.classLoader.getResourceAsStream("adjacencylist.json")

        val adjacencyListType = object : TypeToken<Map<String, Set<String>>>() {}.type
        val data: AdjacencyList = gson.fromJson(InputStreamReader(jsonFile), adjacencyListType)

        nodesData(data) //The method should not throw an exception
    }
}
