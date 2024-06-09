// Путь: src/main/kotlin/com/example/ticketbookingsystem/controller/TicketOrderController.kt
package com.example.ticketbookingsystem.controller

import com.example.ticketbookingsystem.entity.TicketOrder
import com.example.ticketbookingsystem.service.TicketOrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST-контроллер для управления заказами на покупку билетов.
 */
@RestController
@RequestMapping("/api/orders")
class TicketOrderController(
    private val orderService: TicketOrderService
) {

    /**
     * Конечная точка для создания нового заказа на покупку билета.
     *
     * @param userId Идентификатор пользователя, который делает заказ.
     * @param fromStationId Идентификатор станции отправления.
     * @param toStationId Идентификатор станции назначения.
     * @return ResponseEntity с созданным заказом или соответствующим сообщением об ошибке.
     */
    @PostMapping
    fun createOrder(
        @RequestParam("userId") userId: Long,
        @RequestParam("fromStationId") fromStationId: Long,
        @RequestParam("toStationId") toStationId: Long
    ): ResponseEntity<Any> {
        val order = orderService.createOrder(userId, fromStationId, toStationId)
        return ResponseEntity.ok(order)
    }

    /**
     * Конечная точка для получения информации о существующем заказе.
     *
     * @param orderId Идентификатор заказа.
     * @return ResponseEntity с информацией о заказе или сообщением "Order not found", если заказ не найден.
     */
    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable("orderId") orderId: Long): ResponseEntity<Any> {
        val order = orderService.getOrder(orderId)
        return if (order != null) {
            ResponseEntity.ok(order)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found")
        }
    }

    /**
     * Конечная точка для обновления статуса существующего заказа.
     *
     * @param orderId Идентификатор заказа.
     * @param status Новый статус заказа (check, success или rejected).
     * @return ResponseEntity с обновленным заказом или соответствующим сообщением об ошибке.
     */
    @PutMapping("/{orderId}/status")
    fun updateOrderStatus(
        @PathVariable("orderId") orderId: Long,
        @RequestParam("status") status: String
    ): ResponseEntity<Any> {
        val order = orderService.getOrder(orderId) ?: return ResponseEntity.notFound().build()

        try {
            val newStatus = TicketOrder.Status.valueOf(status.uppercase())
            order.status = newStatus
            orderService.updateOrder(order)
            return ResponseEntity.ok(order)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.badRequest().body("Invalid status: $status")
        }
    }
}