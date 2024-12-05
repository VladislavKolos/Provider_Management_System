package org.example.mapper.extension

import org.example.dto.responsedto.UserResponseDto
import org.example.model.User

fun User.toResponseDto(): UserResponseDto = UserResponseDto(
    id = this.id,
    username = this.usernameField,
    email = this.email,
    phone = this.phone,
    role = this.role.toResponseDto(),
    status = this.status.toResponseDto()
)