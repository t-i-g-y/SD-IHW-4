// Путь: src/main/kotlin/com/example/ticketbookingsystem/entity/TicketOrder.kt
package com.example.ticketbookingsystem.entity

import javax.persistence.*

@Entity
@Table(name = "ticket_order")
data class TicketOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val fromStationId: Long,

    @Column(nullable = false)
    val toStationId: Long,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: Status,

    @Column(nullable = false)
    val created: Long = System.currentTimeMillis()
) {
    constructor() : this(0, 0, 0, 0, Status.CHECK, 0) {

    }

    enum class Status {
        CHECK, SUCCESS, REJECTED
    }
}