package org.example.repository

import org.example.model.Promotion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PromotionRepository: JpaRepository<Promotion, UUID> {

    override fun findAll(pageable: Pageable): Page<Promotion>
}