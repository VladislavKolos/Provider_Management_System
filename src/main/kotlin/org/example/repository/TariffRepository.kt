package org.example.repository

import org.example.model.Tariff
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TariffRepository : JpaRepository<Tariff, UUID> {

    fun existsByName(name: String): Boolean

    fun findTariffById(id: UUID): Tariff?

    @Query(" SELECT t FROM Tariff t")
    fun findAllTariffs(pageable: Pageable): Page<Tariff>?
}