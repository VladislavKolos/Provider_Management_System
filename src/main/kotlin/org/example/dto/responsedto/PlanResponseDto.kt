package org.example.dto.responsedto

import java.time.LocalDate
import java.util.*

data class PlanResponseDto(
    val id: UUID,
    val name: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val tariff: TariffResponseDto
)
