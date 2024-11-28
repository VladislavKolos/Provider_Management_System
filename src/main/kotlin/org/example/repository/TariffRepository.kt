package org.example.repository

import org.example.model.Tariff
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TariffRepository : JpaRepository<Tariff, UUID> {

    fun existsByName(name: String): Boolean

    override fun findAll(pageable: Pageable): Page<Tariff>
}