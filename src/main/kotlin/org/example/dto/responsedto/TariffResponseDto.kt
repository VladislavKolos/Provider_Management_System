package org.example.dto.responsedto

import java.math.BigDecimal
import java.util.*

data class TariffResponseDto(
    val id: UUID,
    val name: String?,
    val description: String,
    val monthlyCost: BigDecimal,
    val dataLimit: Double,
    val voiceLimit: Double
)
