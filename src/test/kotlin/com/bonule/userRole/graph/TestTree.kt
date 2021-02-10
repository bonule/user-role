package com.bonule.userRole.graph

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestTree {

    @Test
    fun `check graph is a tree`() {
        val graph = wellFormedTree()
        val tree = Tree(graph)

        assertThat(tree.rootNode).isEqualTo(0)
    }

    @Test
    fun `get children of node 1`() {
        val graph = wellFormedTree()
        val tree = Tree(graph)

        val children = tree.getChildren(1)
        assertThat(children.size).isEqualTo(1)
    }

    @Test
    fun `get children of node 0`() {
        val graph = wellFormedTree()
        val tree = Tree(graph)

        val children = tree.getChildren(0)
        assertThat(children.size).isEqualTo(5)
    }

    @Test
    fun `get children of leaf node`() {
        val graph = wellFormedTree()
        val tree = Tree(graph)

        val children = tree.getChildren(5)
        assertThat(children.size).isEqualTo(0)
    }

    @Test
    fun `get children of none existing node`() {
        val graph = wellFormedTree()
        val tree = Tree(graph)

        assertThrows<GraphException> {
            tree.getChildren(10)
        }
    }

    @Test
    fun `tree is invalid as it has two roots`() {
        val graph = invalidTwoRootTree()

        assertThrows<GraphException> {
            Tree(graph)
        }
    }

    @Test
    fun `tree is invalid as a node has more than one parent`() {
        val graph = invalidMoreThanOneParent()

        assertThrows<GraphException> {
            Tree(graph)
        }
    }

    @Test
    fun `tree is invalid as it is a circular graph`() {
        val graph = invalidCyclicTree()

        assertThrows<GraphException> {
            Tree(graph)
        }
    }

    private fun wellFormedTree(): Graph {
        val graph = Graph()

        graph.addEdge(0, 1)
        graph.addEdge(0, 2)
        graph.addEdge(0, 3)
        graph.addEdge(1, 4)
        graph.addEdge(2, 5)

        return graph
    }

    private fun invalidTwoRootTree(): Graph {
        // First Tree
        val graph = wellFormedTree()

        // Second Tree
        graph.addEdge(6, 3)

        return graph
    }

    private fun invalidMoreThanOneParent(): Graph {
        val graph = wellFormedTree()

        // Add another parent for an existing node
        graph.addEdge(1, 5)

        return graph
    }

    private fun invalidCyclicTree(): Graph {
        val graph = wellFormedTree()

        // Add a cycle to the graph
        graph.addEdge(5, 1)

        return graph
    }
}