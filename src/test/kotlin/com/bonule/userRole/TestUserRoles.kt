package com.bonule.userRole

import com.bonule.userRole.graph.GraphException
import com.bonule.userRole.model.Role
import com.bonule.userRole.model.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestUserRoles {

    @Test
    fun `validate that getSubOrdinates of role 3 is returning the right values`() {
        val userRoles = UserRoles(roles(), users())
        val subOrdinates = userRoles.getSubOrdinates(3)

        assertThat(subOrdinates.size).isEqualTo(2)
        assertThat(subOrdinates.contains(User(2, "Emily Employee", 4))).isTrue
        assertThat(subOrdinates.contains(User(5, "Steve Trainer", 5))).isTrue
    }

    @Test
    fun `validate that getSubOrdinates of role 1 is returning the right values`() {
        val userRoles = UserRoles(roles(), users())
        val subOrdinates = userRoles.getSubOrdinates(1)

        assertThat(subOrdinates.size).isEqualTo(4)
        assertThat(subOrdinates.contains(User(2, "Emily Employee", 4))).isTrue
        assertThat(subOrdinates.contains(User(5, "Steve Trainer", 5))).isTrue
        assertThat(subOrdinates.contains(User(3, "Sam Supervisor", 3))).isTrue
        assertThat(subOrdinates.contains(User(4, "Mary Manager", 2))).isTrue
    }

    @Test
    fun `validate that getSubOrdinates functions correctly for leaf node`() {
        val userRoles = UserRoles(roles(), users())
        val subOrdinates = userRoles.getSubOrdinates(5)

        assertThat(subOrdinates).isEmpty()
    }

    @Test
    fun `user id does not exist`() {
        val userRoles = UserRoles(roles(), users())
        assertThrows<GraphException> {
            userRoles.getSubOrdinates(100)
        }
    }

    @Test
    fun `user role is not valid`() {
        assertThrows<GraphException> {
            UserRoles(roles(), invalidUserRole())
        }
    }

    @Test
    fun `role tree is disconnected`() {
        assertThrows<GraphException> {
            UserRoles(invalidRolesNotConnected(), users())
        }
    }

    @Test
    fun `role tree is cyclic`() {
        assertThrows<GraphException> {
            UserRoles(invalidRolesCyclic(), users())
        }
    }

    @Test
    fun `role ids not contiguous`() {
        assertThrows<GraphException> {
            UserRoles(invalidRolesNotContiguous(), users())
        }
    }

    private fun roles(): List<Role> {
        return arrayListOf(
            Role(1, "System Administrator", 0),
            Role(2, "Location Manager", 1),
            Role(3, "Supervisor", 2),
            Role(4, "Employee", 3),
            Role(5, "Trainer", 3)
        )
    }

    private fun users(): List<User> {
        return arrayListOf(
        User(1, "Adam Admin", 1),
        User(2, "Emily Employee", 4),
        User(3, "Sam Supervisor", 3),
        User(4, "Mary Manager", 2),
        User(5, "Steve Trainer", 5)
        )
    }

    private fun invalidUserRole(): List<User> {
        val users = users().toMutableList()
        users.add(User(6, "Jane Senior Manager", 100))

        return users
    }

    private fun invalidRolesNotConnected(): List<Role> {
        val roles = roles().toMutableList()
        roles.add(Role(6, "Disconnected Role", 7))

        return roles
    }

    private fun invalidRolesCyclic(): List<Role> {
        val roles = roles().toMutableList()
        roles.add(Role(1, "Disconnected Role", 4))

        return roles
    }

    private fun invalidRolesNotContiguous(): List<Role> {
        val roles = roles().toMutableList()
        roles.add(Role(7, "Jane Senior  Manager", 8))

        return roles
    }
}