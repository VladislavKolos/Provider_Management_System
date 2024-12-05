package org.example.repository.custom

import org.example.model.Tariff
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.math.BigDecimal

interface CustomTariffRepository {

    fun findTariffsByCostRange(minCost: BigDecimal, maxCost: BigDecimal, pageable: Pageable): Page<Tariff>?

    fun findTariffsWithActivePlans(pageable: Pageable): Page<Tariff>?
}