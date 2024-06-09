package com.example.ticketbookingsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TicketBookingSystemApplication

fun main(args: Array<String>) {
    runApplication<TicketBookingSystemApplication>(*args)
}