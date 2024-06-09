// Путь: src/main/kotlin/com/example/ticketbookingsystem/repository/UserRepository.kt
package com.example.ticketbookingsystem.repository

import com.example.ticketbookingsystem.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}