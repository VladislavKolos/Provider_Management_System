package org.example.service.impl

import org.example.model.Tariff
import org.example.repository.TariffRepository
import org.example.service.TariffService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TariffServiceImpl(
    private val tariffRepository: TariffRepository

) : TariffService {

    @Transactional(readOnly = true)
    override fun getTariffById(id: UUID): Tariff {
        return tariffRepository.findTariffById(id)
            ?: throw NoSuchElementException("Tariff with this ID: $id was not found in the database")
    }

    @Transactional(readOnly = true)
    override fun getAllTariffs(pageable: Pageable): Page<Tariff> {
        return tariffRepository.findAllTariffs(pageable)
            ?: throw NoSuchElementException("No tariffs were found in the database")
    }
}