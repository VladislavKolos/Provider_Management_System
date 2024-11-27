package org.example.repository

import org.example.model.Plan
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PlanRepository: JpaRepository<Plan, UUID> {

    fun existsByName(name: String): Boolean

    override fun findAll(pageable: Pageable): Page<Plan>
}