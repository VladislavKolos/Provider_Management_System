package org.example.service

import org.example.model.Tariff
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*

@Component
interface TariffService {

    fun getTariffById(id: UUID): Tariff

    fun getAllTariffs(pageable: Pageable): Page<Tariff>
}