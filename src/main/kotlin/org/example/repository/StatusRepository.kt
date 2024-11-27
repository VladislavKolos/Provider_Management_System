package org.example.repository

import org.example.model.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StatusRepository : JpaRepository<Status, UUID> {

    fun findByName(name: String): Status?

    fun existsByName(name: String): Boolean
}