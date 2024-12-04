package org.example.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.service.JwtService
import org.example.service.UserService
import org.example.util.ProviderConstantUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val userService: UserService

) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val token = request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.removePrefix("Bearer ")
            ?: return denyAccess(response, "Authorization header is missing or invalid")

        val username = jwtService.extractUsername(token)
            ?: return denyAccess(response, "Username could not be extracted from the token")

        val userDetails = userDetailsService.loadUserByUsername(username)
        val user = userService.getUserByUsername(username)

        if (user.status.name in listOf(
                ProviderConstantUtil.USER_STATUS_BANNED,
                ProviderConstantUtil.USER_STATUS_INACTIVE
            )
        ) {
            return denyAccess(response, "Access Denied: User is banned or token is invalid.")
        }

        if (!jwtService.isTokenValid(token, userDetails)) {
            return denyAccess(response, "Access Denied: User is banned or token is invalid.")
        }

        setAuthentication(userDetails, request)
        filterChain.doFilter(request, response)
    }

    private fun denyAccess(response: HttpServletResponse, message: String) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(message)
    }

    private fun setAuthentication(
        userDetails: UserDetails, request: HttpServletRequest
    ) {

        val auth = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        ).apply {
            details = WebAuthenticationDetailsSource().buildDetails(request)
        }
        SecurityContextHolder.getContext().authentication = auth
    }


}