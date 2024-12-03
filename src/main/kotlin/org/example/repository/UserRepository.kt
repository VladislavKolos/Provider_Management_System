package org.example.repository

import org.example.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    @Query(
        """
    SELECT COUNT(u) > 0 
    FROM User u 
    WHERE u.email = :email AND u.status.name = "banned"
    """
    )
    fun isUserBannedByEmail(
        @Param("email") email: String
    ): Boolean

    @Query(
        """
    SELECT COUNT(u) > 0 
    FROM User u 
    WHERE u.phone = :phone AND u.status.name = "banned"
    """
    )
    fun isUserBannedByPhone(
        @Param("phone") phone: String
    ): Boolean

    fun findByUsernameField(username: String): User?

    fun existsByUsernameField(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun existsByPhone(phone: String): Boolean
}