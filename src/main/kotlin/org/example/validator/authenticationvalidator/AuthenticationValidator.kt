package org.example.validator.authenticationvalidator

import org.example.dto.requestdto.RegisterRequestDto
import org.example.service.UserService
import org.example.util.ProviderConstantUtil
import org.springframework.stereotype.Component

@Component
class AuthenticationValidator(
    private val userService: UserService

) {
    fun isUserBannedByEmail(request: RegisterRequestDto): Boolean {
        return userService.getUserByEmail(request.email)
            .status
            .id == ProviderConstantUtil.USER_STATUS_BANNED
    }

    fun isUserBannedByPhone(request: RegisterRequestDto): Boolean {
        return userService.getUserByPhone(request.phone)
            .status
            .id == ProviderConstantUtil.USER_STATUS_BANNED
    }
}