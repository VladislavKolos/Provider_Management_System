package org.example.mapper.extension

import org.example.dto.responsedto.StatusResponseDto
import org.example.model.Status

fun Status.toResponseDto(): StatusResponseDto = StatusResponseDto(
    id = this.id,
    name = this.name
)