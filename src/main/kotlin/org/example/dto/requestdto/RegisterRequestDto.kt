package org.example.dto.requestdto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequestDto(

    @field:Size(min = 3, max = 32)
    @field:NotBlank
    val username: String,

    @field:Size(min = 8, max = 256)
    @field:NotBlank
    val password: String,

    @field:Email
    @field:NotBlank
    val email: String,

    @field:Size(min = 10, max = 18)
    @field:NotBlank
    val phone: String
)
