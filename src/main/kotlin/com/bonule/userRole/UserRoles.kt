package com.bonule.userRole

import com.bonule.userRole.graph.*
import com.bonule.userRole.model.Role
import com.bonule.userRole.model.User

/**
 * Given list of roles and users return all of the subordinates for a given user
 */

class UserRoles(roles:List<Role>, users:List<User>) {
    private val tree: Tree
    private val mapRoleToUser = mutableMapOf<Int, User>()
    private val mapUserIdToUser = mutableMapOf<Long, User>()

    init {
        val roleIds = HashSet<Int>()

        val graph = Graph()
        for (role in roles) {
            graph.addEdge(role.parent, role.id)
            roleIds.add(role.id)
        }

        // Validate contiguous set of roleIds
        checkContiguousRoleIds(roleIds)

        // Validate that the graph is a tree
        tree = Tree(graph)

        for (user in users) {
            mapRoleToUser[user.role] = user
            mapUserIdToUser[user.id] = user

            if (!roleIds.contains(user.role)) {
                throw GraphException("The user role ${user.role} is not a valid role")
            }
        }
    }

    fun getSubOrdinates(userId: Long): Set<User> {
        val roleId = mapUserIdToUser[userId]?.role ?: throw GraphException("User Id $userId does not exist")

        val roles = tree.getChildren(roleId)

        val users = mutableSetOf<User>()
        // Get all of the Users that have that role
        for (role in roles) {
            mapRoleToUser[role]?.let { users.add(it) }
        }

        return users
    }

    /**
     * Assumption that the roleIds are contiguous starting from 0
     */
    private fun checkContiguousRoleIds(roleIds: Set<Int>) {
        val sortedRoleIds = roleIds.sorted()

        for (i in 1 until sortedRoleIds.size) {
            if (sortedRoleIds[i] - sortedRoleIds[i - 1] > 1)
                throw GraphException("roleIds are not contiguous, gap exists between ${sortedRoleIds[i]} " +
                        "and sortedRoleIds[i - 1]")
        }
    }
}

