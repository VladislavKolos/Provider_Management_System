package org.example.mapper.extension

import org.example.dto.responsedto.RoleResponseDto
import org.example.model.Role

fun Role.toResponseDto(): RoleResponseDto = RoleResponseDto(
    id = this.id,
    name = this.name
)