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
        runCatching {
            request.getAuthorizationToken()
                ?.let { token -> jwtService.extractUsername(token) to token }
                ?.takeIf { (username, _) -> username != null }
                ?.let { (username, token) ->
                    val userDetails = userDetailsService.loadUserByUsername(username!!)
                    val user = userService.getUserByUsername(username)

                    user.takeIf {
                        it.status.name !in listOf(
                            ProviderConstantUtil.USER_STATUS_BANNED,
                            ProviderConstantUtil.USER_STATUS_INACTIVE
                        )
                    }
                        ?.takeIf { jwtService.isTokenValid(token, userDetails) }
                        ?.also {
                            val auth = UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.authorities
                            ).apply {
                                details = WebAuthenticationDetailsSource().buildDetails(request)
                            }
                            SecurityContextHolder.getContext().authentication = auth

                        } ?: run {
                        response.status = HttpServletResponse.SC_FORBIDDEN
                        response.writer.write("Access Denied: User is banned or token is invalid.")
                        return
                    }
                }
        }.onFailure { exception ->
            throw exception

        }.onSuccess {
            filterChain.doFilter(request, response)
        }
    }

    private fun HttpServletRequest.getAuthorizationToken(): String? {
        return getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.removePrefix("Bearer ")
    }


}