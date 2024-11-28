package org.example.repository

import org.example.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByUsernameField(username: String): User?

    fun findByEmail(email: String): User?

    fun findByPhone(phone: String): User?

    fun existsByUsernameField(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun existsByPhone(phone: String): Boolean
}