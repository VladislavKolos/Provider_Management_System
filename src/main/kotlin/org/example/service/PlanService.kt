package org.example.service

import org.example.model.Plan
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*

@Component
interface PlanService {

    fun getPlanById(id: UUID): Plan

    fun getAllPlans(pageable: Pageable): Page<Plan>
}