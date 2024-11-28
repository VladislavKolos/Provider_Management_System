package org.example.repository

import org.example.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, UUID> {

    fun existsByName(name: String): Boolean

    fun findRoleById(id: UUID): Role?
}