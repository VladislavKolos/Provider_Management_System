package org.example.repository

import org.example.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoleRepository : JpaRepository<Role, UUID> {

    fun existsByName(name: String): Boolean
}