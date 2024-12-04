package org.example.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.model.Status
import org.example.model.User
import org.example.service.JwtService
import org.example.service.UserService
import org.example.util.ProviderConstantUtil
import org.junit.jupiter.api.AfterEach
import org.mockito.kotlin.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import java.io.PrintWriter
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible
import kotlin.test.Test
import kotlin.test.assertNotNull

class JwtAuthenticationFilterTest {
    private val jwtService: JwtService = mock()
    private val userDetailsService: UserDetailsService = mock()
    private val userService: UserService = mock()
    private val filter: JwtAuthenticationFilter = JwtAuthenticationFilter(jwtService, userDetailsService, userService)
    private val request: HttpServletRequest = mock()
    private val response: HttpServletResponse = mock()
    private val filterChain: FilterChain = mock()

    @Test
    fun `should authenticate valid user with valid token`() {
        val username = "validUser"
        val token = "validToken"

        mockValidTokenBehavior(token, username, ProviderConstantUtil.USER_STATUS_ACTIVE, true)

        invokeDoFilterInternal(request, response, filterChain)

        verify(filterChain).doFilter(request, response)
        assertNotNull(SecurityContextHolder.getContext().authentication)
        verifyNoMoreInteractions(filterChain, response)
    }

    @Test
    fun `should deny access if user is banned`() {
        val username = "bannedUser"
        val token = "validToken"

        mockValidTokenBehavior(token, username, ProviderConstantUtil.USER_STATUS_BANNED, true)

        val writer = mock<PrintWriter>()
        whenever(response.writer).thenReturn(writer)

        invokeDoFilterInternal(request, response, filterChain)

        verify(response).status = HttpServletResponse.SC_FORBIDDEN
        verify(response.writer).write("Access Denied: User is banned or token is invalid.")
        verify(filterChain, never()).doFilter(request, response)
        verifyNoMoreInteractions(filterChain)
    }

    @Test
    fun `should deny access if token is invalid`() {
        val username = "validUser"
        val token = "invalidToken"

        mockValidTokenBehavior(token, username, ProviderConstantUtil.USER_STATUS_ACTIVE, false)

        val writer = mock<PrintWriter>()
        whenever(response.writer).thenReturn(writer)

        invokeDoFilterInternal(request, response, filterChain)

        verify(response).status = HttpServletResponse.SC_FORBIDDEN
        verify(response.writer).write("Access Denied: User is banned or token is invalid.")
        verify(filterChain, never()).doFilter(request, response)
        verifyNoMoreInteractions(filterChain)
    }

    @Test
    fun `should deny access when Authorization header is missing`() {
        whenever(request.getHeader("Authorization")).thenReturn(null)

        val writer = mock<PrintWriter>()
        whenever(response.writer).thenReturn(writer)

        invokeDoFilterInternal(request, response, filterChain)

        verify(response).status = HttpServletResponse.SC_FORBIDDEN
        verify(response.writer).write("Authorization header is missing or invalid")
        verify(filterChain, never()).doFilter(request, response)
        verifyNoMoreInteractions(filterChain, jwtService, userService, userDetailsService)
    }

    @AfterEach
    fun tearDown() {
        SecurityContextHolder.clearContext()
    }

    private fun mockValidTokenBehavior(
        token: String,
        username: String,
        status: String,
        tokenValidResult: Boolean
    ) {

        val userDetails: UserDetails = mock {
            on { this.username } doReturn username
        }

        val mockStatus = mock<Status> {
            whenever(it.name).thenReturn(status)
        }

        val mockUser = mock<User> {
            whenever(it.status).thenReturn(mockStatus)
        }

        whenever(request.getHeader("Authorization")).thenReturn("Bearer $token")
        whenever(jwtService.extractUsername(token)).thenReturn(username)
        whenever(jwtService.isTokenValid(token, userDetails)).thenReturn(tokenValidResult)
        whenever(userDetailsService.loadUserByUsername(eq(username))).thenReturn(userDetails)
        whenever(userService.getUserByUsername(eq(username))).thenReturn(mockUser)
    }

    private fun invokeDoFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val method = filter::class.declaredMemberFunctions
            .first { it.name == "doFilterInternal" }
        method.isAccessible = true
        method.call(filter, request, response, filterChain)
    }
}