// Путь: src/main/kotlin/com/example/ticketbookingsystem/repository/SessionRepository.kt
package com.example.ticketbookingsystem.repository

import com.example.ticketbookingsystem.entity.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Long> {
    fun findByToken(token: String): Session?
}