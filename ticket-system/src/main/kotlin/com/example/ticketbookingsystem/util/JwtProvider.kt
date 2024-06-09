// Путь: src/main/kotlin/com/example/ticketbookingsystem/util/JwtProvider.kt
package com.example.ticketbookingsystem.util

import com.example.ticketbookingsystem.entity.Session
import com.example.ticketbookingsystem.repository.SessionRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.util.*

/**
 * Класс для генерации и валидации JSON Web Tokens (JWT).
 */
@Component
class JwtProvider(
    private val sessionRepository: SessionRepository
) {
    private val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    /**
     * Генерирует JWT токен для указанного идентификатора пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @return Сгенерированный JWT токен.
     */
    fun generateToken(userId: Long): String {
        val claims = Jwts.claims().setSubject(userId.toString())
        val now = Date()
        val validity = Date(now.time + 3600000) // 1 час
        val token = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key)
            .compact()

        // Сохранение токена и времени его истечения в базе данных
        val session = Session(userId = userId, token = token, expires = validity.toInstant())
        sessionRepository.save(session)

        return token
    }

    /**
     * Валидирует JWT токен.
     *
     * @param token JWT токен для валидации.
     * @return true, если токен валиден, иначе false.
     */
    fun validateToken(token: String): Boolean {
        val session = sessionRepository.findByToken(token) ?: return false
        return !session.expires.isBefore(Instant.now())
    }

    /**
     * Извлекает идентификатор пользователя из JWT токена.
     *
     * @param token JWT токен.
     * @return Идентификатор пользователя.
     * @throws IllegalArgumentException, если токен невалиден.
     */
    fun getUserIdFromToken(token: String): Long {
        val session = sessionRepository.findByToken(token) ?: throw IllegalArgumentException("Invalid token")
        val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return claims.subject.toLong()
    }
}