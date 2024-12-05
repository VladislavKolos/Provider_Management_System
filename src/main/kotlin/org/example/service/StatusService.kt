package org.example.service

import org.example.model.Status
import org.springframework.stereotype.Component
import java.util.*

@Component
interface StatusService {

    fun getStatusEntityById(id: UUID): Status

    fun getStatusEntityByName(name: String): Status
}