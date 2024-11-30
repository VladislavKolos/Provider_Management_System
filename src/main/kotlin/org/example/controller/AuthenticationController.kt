package org.example.controller

import org.example.dto.requestdto.AuthenticationRequestDto
import org.example.dto.requestdto.RegisterRequestDto
import org.example.dto.responsedto.AuthenticationResponseDto
import org.example.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/auth")
class AuthenticationController(

    private val service: AuthenticationService

) {

    @PostMapping("/signup")
    fun register(@RequestBody request: RegisterRequestDto): ResponseEntity<AuthenticationResponseDto> =
        service.register(request)
            .let { ResponseEntity.ok(it) }

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthenticationRequestDto): ResponseEntity<AuthenticationResponseDto> =
        service.authenticate(request)
            .let { ResponseEntity.ok(it) }
}