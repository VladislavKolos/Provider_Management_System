package org.example.repository

import org.example.model.EmailToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EmailTokenRepository : JpaRepository<EmailToken, UUID> {

    fun findByToken(token: String): EmailToken?
}