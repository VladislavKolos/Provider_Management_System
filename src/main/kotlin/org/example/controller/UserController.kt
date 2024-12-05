package org.example.controller

import org.example.dto.responsedto.UserResponseDto
import org.example.mapper.extension.toResponseDto
import org.example.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(

    private val userService: UserService

) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/profile")
    fun getClientUserProfile(authentication: Authentication): ResponseEntity<UserResponseDto> {
        val username = authentication.name

        val response = userService.getUserByUsername(username).toResponseDto()
        logger.info("Successfully retrieved profile for client with Username: $username")

        return ResponseEntity.ok(response)
    }
}