package org.example.repository

import org.example.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    @Query(
        """
        SELECT u FROM User u 
        WHERE u.email = :email AND u.status.name = :name
    """
    )
    fun findBannedUserByEmail(
        @Param("email") email: String,
        @Param("name") name: String
    ): User?

    @Query(
        """
        SELECT u FROM User u 
        WHERE u.phone = :phone AND u.status.name = :name
    """
    )
    fun findBannedUserByPhone(
        @Param("phone") phone: String,
        @Param("name") statusName: String
    ): User?

    fun findByUsernameField(username: String): User?

    fun existsByUsernameField(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun existsByPhone(phone: String): Boolean
}