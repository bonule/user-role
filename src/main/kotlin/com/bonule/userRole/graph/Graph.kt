package com.bonule.userRole.graph

/**
 * Uses an adjacency list to hold the graph
 *
 * Assumption: The nodes numbers are consecutive and start at 0
 */

class Graph {
    private var adjacencyList = mutableListOf<MutableList<Int>>()

    fun addEdge(source: Int, destination: Int) {
        if (source < 0) {
            throw GraphException("source node '$source' cannot be a negative number")
        }

        if (destination < 0) {
            throw GraphException("destination node '$destination' cannot be a negative number")
        }

        //Increase the size of the adjacencyList to accommodate the additional nodes
        while (adjacencyList.size <= maxOf(source, destination)) {
            adjacencyList.add(mutableListOf())
        }

        adjacencyList[source].add(destination)
    }

    fun adjNodes(node: Int): List<Int> {
        if (node >= adjacencyList.size || node < 0) {
            throw GraphException("'$node' not found in adjacencyList. Has the node been added?")
        }

        return adjacencyList[node]
    }

    fun size() = adjacencyList.size

    fun reverseGraph(): Graph {
        val graph = Graph()

        for ((node, adjacentNodes) in adjacencyList.withIndex()) {
            for (adjacentNode in adjacentNodes) {
                graph.addEdge(adjacentNode, node)
            }
        }

        return graph
    }


}
