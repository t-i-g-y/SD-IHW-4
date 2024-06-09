// Путь: src/main/kotlin/com/example/ticketbookingsystem/entity/Session.kt
package com.example.ticketbookingsystem.entity

import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "session")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val token: String,

    @Column(nullable = false)
    val expires: Instant
) {
    constructor() : this(0, 0, "", Instant.now()) {

    }
}