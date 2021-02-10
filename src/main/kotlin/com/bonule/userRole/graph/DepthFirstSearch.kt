package com.bonule.userRole.graph

import java.util.*

/**
 * Use a depth first search to determine all of the nodes reachable from the start node
 */


class DepthFirstSearch(private val graph: Graph) {

    fun search(startNode: Int): List<Path> {
        val paths = mutableListOf<Path>()
        searchPath(startNode, paths)

        return paths
    }

    private fun searchPath(startNode: Int,
                           paths: MutableList<Path>,
                           visited: MutableSet<Int> = HashSet<Int>(),
                           currentPath: List<Int> = emptyList()) {
        val newCurrentPath = currentPath.toMutableList()
        newCurrentPath.add(startNode)

        if (visited.contains(startNode)) {
            throw GraphException("Graph is cyclic. The path $newCurrentPath contains a cycle")
        }

        visited.add(startNode)

        paths.add(Path(newCurrentPath))

        val adjacentNodes = graph.adjNodes(startNode)

        for (adjacentNode in adjacentNodes) {
            searchPath(adjacentNode, paths, visited, newCurrentPath)
        }
    }
}