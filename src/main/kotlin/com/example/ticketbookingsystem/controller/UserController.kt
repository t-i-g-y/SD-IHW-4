// Путь: src/main/kotlin/com/example/ticketbookingsystem/controller/UserController.kt
package com.example.ticketbookingsystem.controller

import com.example.ticketbookingsystem.auth.AuthenticationService
import com.example.ticketbookingsystem.entity.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST-контроллер для управления пользователями и их аутентификацией.
 */
@RestController
@RequestMapping("/api/users")
class UserController(private val authService: AuthenticationService) {

    /**
     * Конечная точка для регистрации нового пользователя.
     *
     * @param nickname Отображаемое имя (ник) пользователя.
     * @param email Адрес электронной почты пользователя.
     * @param password Пароль пользователя.
     * @return ResponseEntity с данными зарегистрированного пользователя.
     */
    @PostMapping("/register")
    fun registerUser(
        @RequestParam("nickname") nickname: String,
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<User> {
        val user = authService.registerUser(nickname, email, password)
        return ResponseEntity.ok(user)
    }

    /**
     * Конечная точка для аутентификации пользователя и получения JWT токена.
     *
     * @param email Адрес электронной почты пользователя.
     * @param password Пароль пользователя.
     * @return ResponseEntity с JWT токеном или сообщением об ошибке, если аутентификация не удалась.
     */
    @PostMapping("/login")
    fun login(
        @RequestParam("email") email: String,
        @RequestParam("password") password: String
    ): ResponseEntity<Any> {
        val token = authService.authenticate(email, password)
        return if (token != null) {
            ResponseEntity.ok(mapOf("token" to token))
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials")
        }
    }

    /**
     * Конечная точка для получения информации о пользователе по JWT токену.
     *
     * @param authHeader Заголовок "Authorization" с JWT токеном в формате "Bearer {token}".
     * @return ResponseEntity с данными пользователя или статусом 404, если пользователь не найден.
     */
    @GetMapping("/{userId}")
    fun getUserInfo(@RequestHeader("Authorization") authHeader: String): ResponseEntity<Any> {
        val token = authHeader.substringAfter("Bearer ")
        val user = authService.getUserInfo(token)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}