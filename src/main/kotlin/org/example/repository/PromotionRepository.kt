package org.example.repository

import org.example.model.Promotion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PromotionRepository: JpaRepository<Promotion, UUID> {

    override fun findAll(pageable: Pageable): Page<Promotion>
}