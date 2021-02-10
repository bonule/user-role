package com.bonule.userRole.graph

/**
 * Validate and initialise a tree structure from a graph
 * Checks that there is only one root and each node has
 * only one parent
 */

class Tree(private val graph: Graph) {
    var rootNode: Int = -1
        private set

    init {
        initTree()
    }

    fun getChildren(startNode: Int): Set<Int> {
        val depthFirstSearch = DepthFirstSearch(graph)
        val paths = depthFirstSearch.search(startNode)

        val nodes = mutableSetOf<Int>()

        // Get all of the unique nodes in the path
        for (path in paths) {
            val node = path.nodes[path.nodes.lastIndex]

            if (startNode != node) {
                nodes.add(node)
            }
        }

        return nodes
    }

    private fun initTree() {
        val reversedGraph = graph.reverseGraph()

        for (i in 0 until reversedGraph.size()) {
            if (reversedGraph.adjNodes(i).isEmpty()) {
                if (rootNode >= 0) {
                    throw GraphException("There should only be one root node. Two identified which are $rootNode and $i")
                }

                rootNode = i
            } else {
                if (reversedGraph.adjNodes(i).size != 1) {
                    throw GraphException(
                        "Each node should only have one parent. " +
                                "Node $i has ${reversedGraph.adjNodes(i).size} parents"
                    )
                }
            }
        }
    }
}