package org.example.controller

import org.example.dto.requestdto.AuthenticationRequestDto
import org.example.dto.requestdto.RegisterRequestDto
import org.example.dto.responsedto.AuthenticationResponseDto
import org.example.service.AuthenticationService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val service: AuthenticationService

) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/signup")
    fun register(@RequestBody request: RegisterRequestDto): ResponseEntity<AuthenticationResponseDto> {
        val response = service.register(request)
        logger.info("User registered successfully: email=${request.email}, phone=${request.phone}")

        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthenticationRequestDto): ResponseEntity<AuthenticationResponseDto> {
        val response = service.authenticate(request)
        logger.info("User authenticated successfully: username=${request.username}")

        return ResponseEntity.ok(response)
    }
}