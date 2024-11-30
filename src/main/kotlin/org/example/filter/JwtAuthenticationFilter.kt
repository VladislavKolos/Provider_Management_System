package org.example.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.service.JwtService
import org.example.service.UserService
import org.example.util.ProviderConstantUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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
        request.getAuthorizationToken()
            ?.takeIf { jwt -> jwtService.extractUsername(jwt) != null }
            ?.run { handleAuthentication(this, request, response) }
            ?: filterChain.doFilter(request, response)

    }

    private fun HttpServletRequest.getAuthorizationToken(): String? {
        return getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.removePrefix("Bearer ")
    }

    private fun handleAuthentication(token: String, request: HttpServletRequest, response: HttpServletResponse) {
        val username = jwtService.extractUsername(token)

        username
            ?.let { userDetailsService.loadUserByUsername(it) to userService.getUserByUsername(it) }
            ?.takeIf { (_, user) ->
                user.status.name !in listOf(
                    ProviderConstantUtil.USER_STATUS_BANNED,
                    ProviderConstantUtil.USER_STATUS_INACTIVE
                )
            }
            ?.takeIf { (userDetails, _) -> jwtService.isTokenValid(token, userDetails) }
            ?.apply {
                val (userDetails, _) = this
                UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                ).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(request)
                }.also {
                    SecurityContextHolder.getContext().authentication = it
                }
            } ?: response.run {
            status = HttpServletResponse.SC_FORBIDDEN
        }
    }

}