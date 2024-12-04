package org.example.service

import org.example.dto.requestdto.AuthenticationRequestDto
import org.example.dto.requestdto.RegisterRequestDto
import org.example.dto.responsedto.AuthenticationResponseDto
import org.example.model.User
import org.example.util.ProviderConstantUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationService(
    private val userService: UserService,
    private val roleService: RoleService,
    private val statusService: StatusService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager

) {

    @Transactional
    fun register(request: RegisterRequestDto): AuthenticationResponseDto {
        userService.checkIfUserBanned(request.email, request.phone)

        return buildUser(request)
            .also { user -> userService.save(user) }
            .let { user -> jwtService.generateToken(user) }
            .let { jwtToken -> AuthenticationResponseDto(jwtToken) }
    }

    @Transactional
    fun authenticate(request: AuthenticationRequestDto): AuthenticationResponseDto {
        return UsernamePasswordAuthenticationToken(request.username, request.password)
            .let { jwtToken ->
                authenticationManager.authenticate(jwtToken)
            }

            .let {
                userService.getUserByUsername(request.username)
            }

            .let { user ->
                jwtService.generateToken(user)
            }

            .let { jwtToken ->
                AuthenticationResponseDto(jwtToken)
            }
    }

    private fun buildUser(request: RegisterRequestDto): User {
        return User(
            usernameField = request.username,
            passwordField = passwordEncoder.encode(request.password),
            email = request.email,
            phone = request.phone,
            status = statusService.getStatusEntityByName(ProviderConstantUtil.USER_STATUS_ACTIVE),
            role = roleService.getRoleEntityByName(ProviderConstantUtil.ROLE_CLIENT)
        )
    }

}