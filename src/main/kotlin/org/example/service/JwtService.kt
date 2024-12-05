package org.example.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.example.util.ProviderConstantUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import kotlin.time.Duration.Companion.milliseconds

@Service
class JwtService(

    @Value("\${jwt.secret}")
    private val secretKey: String

) {

    fun generateToken(userDetails: UserDetails): String =
        generateToken(emptyMap(), userDetails)

    fun generateToken(extractClaims: Map<String, Any>, userDetails: UserDetails): String {
        val now = Clock.System.now()
        val expiration = now + ProviderConstantUtil.ADDITIONAL_MILLIS.milliseconds

        return Jwts.builder()
            .setClaims(extractClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date.from(now.toJavaInstant()))
            .setExpiration(Date.from(expiration.toJavaInstant()))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        return extractUsername(token)
            ?.takeIf { it == userDetails.username }
            ?.let { !isTokenExpired(token) }
            ?: false
    }

    fun extractUsername(token: String): String? =
        extractClaim(token) { it.subject }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String): Boolean =
        extractExpiration(token) < Clock.System.now()

    private fun extractExpiration(token: String): Instant =
        extractClaim(token) { it.expiration.toInstant().toKotlinInstant() }

    private inline fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T =
        claimsResolver(extractAllClaims(token))

    private fun getSignInKey(): Key =
        Decoders.BASE64.decode(secretKey).let { Keys.hmacShaKeyFor(it) }
}