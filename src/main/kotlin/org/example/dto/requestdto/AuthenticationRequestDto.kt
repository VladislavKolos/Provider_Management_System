package org.example.dto.requestdto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AuthenticationRequestDto(

    @field:Size(min = 3, max = 32)
    @field:NotBlank
    val username: String,

    @field:Size(min = 8, max = 256)
    @field:NotBlank
    val password: String
)
