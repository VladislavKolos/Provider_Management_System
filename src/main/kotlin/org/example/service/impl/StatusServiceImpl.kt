package org.example.service.impl

import org.example.model.Status
import org.example.repository.StatusRepository
import org.example.service.StatusService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StatusServiceImpl(
    private val statusRepository: StatusRepository

) : StatusService {

    @Transactional(readOnly = true)
    override fun getStatusEntityById(id: UUID): Status {
        return statusRepository.findStatusById(id)
            ?: throw NoSuchElementException("Status with ID: $id not found")
    }


}