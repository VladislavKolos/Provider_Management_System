package org.example.repository

import org.example.model.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StatusRepository : JpaRepository<Status, UUID> {

    fun findByName(name: String): Status?

    fun existsByName(name: String): Boolean

    fun findStatusById(id: UUID): Status?
}