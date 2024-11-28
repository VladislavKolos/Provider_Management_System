package org.example.util

import org.example.model.User
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

object RecipientCurrentClientIdUtil {

    fun getCurrentClientId(): UUID {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authentication found in security context")

        val user = authentication.principal as? User
            ?: throw IllegalStateException("Authentication principal is not of type User")

        return user.id
    }
}