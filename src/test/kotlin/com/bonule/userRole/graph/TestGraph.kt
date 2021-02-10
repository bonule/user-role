package com.bonule.userRole.graph

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestGraph {

    @Test
    fun `add edges to graph in ascending order`() {
        val graph = wellFormedGraph()

        assertThat(graph.adjNodes(0).size).isEqualTo(3)
        assertThat(graph.adjNodes(1).size).isEqualTo(1)
        assertThat(graph.adjNodes(2).size).isEqualTo(1)
    }

    @Test
    fun `add edges to graph in any order`() {
        val graph = Graph()

        graph.addEdge(2, 5)
        graph.addEdge(0, 1)
        graph.addEdge(0, 2)
        graph.addEdge(0, 3)
        graph.addEdge(1, 4)

        assertThat(graph.adjNodes(0).size).isEqualTo(3)
        assertThat(graph.adjNodes(1).size).isEqualTo(1)
        assertThat(graph.adjNodes(2).size).isEqualTo(1)
    }

    @Test
    fun `check edges for the added nodes`() {
        val graph = wellFormedGraph()

        val adjacentNodes0 = graph.adjNodes(0)

        assertThat(adjacentNodes0.contains(1)).isTrue
        assertThat(adjacentNodes0.contains(2)).isTrue
        assertThat(adjacentNodes0.contains(3)).isTrue

        val adjacentNodes1 = graph.adjNodes(1)

        assertThat(adjacentNodes1.contains(4)).isTrue

        val adjacentNodes2 = graph.adjNodes(2)

        assertThat(adjacentNodes2.contains(5)).isTrue
    }

    @Test
    fun `check for node that doesn't exist in graph`() {
        val graph = wellFormedGraph()

        assertThrows<GraphException> {
            graph.adjNodes(6)
        }

        assertThrows<GraphException> {
            graph.adjNodes(-1)
        }
    }

    @Test
    fun `add an edge which has a negative number`() {
        val graph = wellFormedGraph()

        assertThrows<GraphException> {
            graph.addEdge(-1, 6)
        }

        assertThrows<GraphException> {
            graph.addEdge(0, -1)
        }
    }

    @Test
    fun `reverse graph`() {
        val graph = wellFormedGraph()

        val reverseGraph = graph.reverseGraph()

        assertThat(reverseGraph.adjNodes(0).size).isEqualTo(0)
        assertThat(reverseGraph.adjNodes(1).size).isEqualTo(1)
        assertThat(reverseGraph.adjNodes(2).size).isEqualTo(1)
        assertThat(reverseGraph.adjNodes(3).size).isEqualTo(1)
        assertThat(reverseGraph.adjNodes(4).size).isEqualTo(1)
        assertThat(reverseGraph.adjNodes(5).size).isEqualTo(1)
    }

    private fun wellFormedGraph(): Graph {
        val graph = Graph()

        graph.addEdge(0, 1)
        graph.addEdge(0, 2)
        graph.addEdge(0, 3)
        graph.addEdge(1, 4)
        graph.addEdge(2, 5)

        return graph
    }
}