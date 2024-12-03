package org.example.repository

import org.example.model.Plan
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface PlanRepository : JpaRepository<Plan, UUID> {

    fun existsByName(name: String): Boolean

    fun findPlanById(id: UUID): Plan?

    @Query(" SELECT p FROM Plan p")
    fun findAllPlans(pageable: Pageable): Page<Plan>?
}