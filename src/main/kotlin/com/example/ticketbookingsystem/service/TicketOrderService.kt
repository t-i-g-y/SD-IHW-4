// Путь: src/main/kotlin/com/example/ticketbookingsystem/service/TicketOrderService.kt
package com.example.ticketbookingsystem.service

import com.example.ticketbookingsystem.entity.TicketOrder
import com.example.ticketbookingsystem.repository.TicketOrderRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import kotlin.concurrent.schedule

/**
 * Сервис для управления заказами на покупку билетов.
 */
@Service
class TicketOrderService(
    private val orderRepository: TicketOrderRepository
) {

    /**
     * Создает новый заказ на покупку билета.
     *
     * @param userId Идентификатор пользователя, который делает заказ.
     * @param fromStationId Идентификатор станции отправления.
     * @param toStationId Идентификатор станции назначения.
     * @return Созданный объект [TicketOrder].
     */
    fun createOrder(
        userId: Long,
        fromStationId: Long,
        toStationId: Long
    ): TicketOrder {
        val order = TicketOrder(
            userId = userId,
            fromStationId = fromStationId,
            toStationId = toStationId,
            status = TicketOrder.Status.CHECK
        )
        return orderRepository.save(order)
    }

    /**
     * Получает информацию о существующем заказе.
     *
     * @param orderId Идентификатор заказа.
     * @return Объект [TicketOrder], если заказ найден, иначе null.
     */
    fun getOrder(orderId: Long): TicketOrder? {
        return orderRepository.findById(orderId).orElse(null)
    }

    /**
     * Метод, который вызывается периодически для обработки заказов в статусе "CHECK".
     * Для каждого заказа в этом статусе случайным образом устанавливается статус "SUCCESS" или "REJECTED".
     */
    @Scheduled(fixedDelay = 5000)
    fun processOrders() {
        val pendingOrders = orderRepository.findByStatus(TicketOrder.Status.CHECK)
        pendingOrders.forEach {
            val randomStatus = if (Random().nextBoolean()) TicketOrder.Status.SUCCESS else TicketOrder.Status.REJECTED
            it.status = randomStatus
            orderRepository.save(it)
        }
    }

    /**
     * Обновляет информацию о существующем заказе в базе данных.
     *
     * @param order Объект [TicketOrder] с обновленными данными.
     */
    fun updateOrder(order: TicketOrder) {
        orderRepository.save(order)
    }
}