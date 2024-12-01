package org.example.dto.responsedto

import java.util.*

data class UserResponseDto(

    val id: UUID,
    val username: String,
    val email: String,
    val phone: String,
    val role: RoleResponseDto,
    val status: StatusResponseDto
)
