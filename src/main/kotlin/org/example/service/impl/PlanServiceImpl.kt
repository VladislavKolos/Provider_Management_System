package org.example.service.impl

import org.example.model.Plan
import org.example.repository.PlanRepository
import org.example.service.PlanService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PlanServiceImpl(

    private val planRepository: PlanRepository

) : PlanService {

    @Transactional(readOnly = true)
    override fun getPlanById(id: UUID): Plan {
        return planRepository.findPlanById(id)
            ?: throw NoSuchElementException("Plan with this ID: $id was not found in the database")
    }

    @Transactional(readOnly = true)
    override fun getAllPlans(pageable: Pageable): Page<Plan> {
        return planRepository.findAllPlans(pageable)
            ?: throw NoSuchElementException("No plans were found in the database")
    }
}