package org.example.config

import org.example.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(

    private val jwtAuthFilter: JwtAuthenticationFilter,

    private val authProvider: AuthenticationProvider,

    private val authEntryPoint: AuthenticationEntryPoint

) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http.run {
            csrf { csrf ->
                csrf.disable()
            }
            authorizeHttpRequests {
                it.requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            }
            sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            exceptionHandling {
                it.authenticationEntryPoint(authEntryPoint)
            }
            authenticationProvider(authProvider)
            addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            build()
        }
    }
}