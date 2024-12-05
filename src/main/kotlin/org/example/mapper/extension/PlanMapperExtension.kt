package org.example.mapper.extension

import org.example.dto.responsedto.PlanResponseDto
import org.example.model.Plan
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

fun Page<Plan>.toResponseDtoPage(): Page<PlanResponseDto> {
    val responseDtoList = this.content.map { it.toResponseDto() }

    return PageImpl(responseDtoList, this.pageable, this.totalElements)
}


fun Plan.toResponseDto(): PlanResponseDto = PlanResponseDto(
    id = this.id,
    name = this.name,
    description = this.description,
    startDate = this.startDate,
    endDate = this.endDate,
    tariff = this.tariff.toResponseDto()
)