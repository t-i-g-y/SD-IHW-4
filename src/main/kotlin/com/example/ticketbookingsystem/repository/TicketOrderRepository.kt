// Путь: src/main/kotlin/com/example/ticketbookingsystem/repository/TicketOrderRepository.kt
package com.example.ticketbookingsystem.repository

import com.example.ticketbookingsystem.entity.TicketOrder
import com.example.ticketbookingsystem.entity.TicketOrder.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketOrderRepository : JpaRepository<TicketOrder, Long> {
    fun findByStatus(status: Status): List<TicketOrder>
}