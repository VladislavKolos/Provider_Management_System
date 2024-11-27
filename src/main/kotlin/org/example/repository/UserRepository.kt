package org.example.repository

import org.example.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    fun findByUsername(username: String): User?

    fun findByEmail(email: String): User?

    fun findByPhone(phone: String): User?

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun existsByPhone(phone: String): Boolean
}