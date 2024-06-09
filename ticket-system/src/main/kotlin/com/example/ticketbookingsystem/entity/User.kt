// Путь: src/main/kotlin/com/example/ticketbookingsystem/entity/User.kt
package com.example.ticketbookingsystem.entity

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val nickname: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val created: Long = System.currentTimeMillis()
) {
    constructor() : this(0, "", "", "") {

    }
}