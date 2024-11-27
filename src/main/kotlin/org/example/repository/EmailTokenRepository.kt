package org.example.repository

import org.example.model.EmailToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EmailTokenRepository : JpaRepository<EmailToken, UUID> {

    fun findByToken(token: String): EmailToken?
}