// Путь: src/main/kotlin/com/example/ticketbookingsystem/entity/Station.kt
package com.example.ticketbookingsystem.entity

import javax.persistence.*

@Entity
@Table(name = "station")
data class Station(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String
) {
    constructor() : this(0, "") {

    }
}