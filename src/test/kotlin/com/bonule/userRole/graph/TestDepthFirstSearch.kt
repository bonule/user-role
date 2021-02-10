package com.bonule.userRole.graph

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestDepthFirstSearch {

    @Test
    fun `breadth first search of the graph`() {
        val breadthFirstSearch = DepthFirstSearch(wellFormedGraph())

        val paths = breadthFirstSearch.search(0)

        assertThat(paths.size).isEqualTo(8)

        assertThat(paths.contains(pathOfNodes(0))).isTrue
        assertThat(paths.contains(pathOfNodes(0, 3))).isTrue
        assertThat(paths.contains(pathOfNodes(0, 2))).isTrue
        assertThat(paths.contains(pathOfNodes(0, 2, 5))).isTrue
        assertThat(paths.contains(pathOfNodes(0, 1))).isTrue
        assertThat(paths.contains(pathOfNodes(0, 1, 4))).isTrue
        assertThat(paths.contains(pathOfNodes(0, 2, 5, 6))).isTrue
        assertThat(paths.contains(pathOfNodes(0, 2, 5, 7))).isTrue
    }

    @Test
    fun `breadth first search of a cyclical graph`() {
        val breadthFirstSearch = DepthFirstSearch(cyclicalGraph())

        assertThrows<GraphException> {
            breadthFirstSearch.search(0)
        }
    }

    private fun wellFormedGraph(): Graph {
        val graph = Graph()

        graph.addEdge(0, 1)
        graph.addEdge(0, 2)
        graph.addEdge(0, 3)
        graph.addEdge(1, 4)
        graph.addEdge(2, 5)
        graph.addEdge(5, 6)
        graph.addEdge(5, 7)

        return graph
    }

    private fun cyclicalGraph(): Graph {
        val graph = Graph()

        graph.addEdge(0, 1)
        graph.addEdge(0, 2)
        graph.addEdge(0, 3)
        graph.addEdge(1, 4)
        graph.addEdge(2, 5)
        graph.addEdge(5, 6)
        graph.addEdge(5, 7)
        graph.addEdge(5, 2)

        return graph
    }

    private fun pathOfNodes(vararg elements: Int) = Path(elements.toMutableList())

}