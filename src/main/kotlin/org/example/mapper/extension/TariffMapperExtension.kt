package org.example.mapper.extension

import org.example.dto.responsedto.TariffResponseDto
import org.example.model.Tariff
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

fun Page<Tariff>.toResponseDtoPage(): Page<TariffResponseDto> {
    val responseDtoList = this.content.map { it.toResponseDto() }

    return PageImpl(responseDtoList, this.pageable, this.totalElements)
}

fun Tariff.toResponseDto(): TariffResponseDto = TariffResponseDto(
    id = this.id,
    name = this.name,
    description = this.description,
    monthlyCost = this.monthlyCost,
    dataLimit = this.dataLimit,
    voiceLimit = this.voiceLimit
)
