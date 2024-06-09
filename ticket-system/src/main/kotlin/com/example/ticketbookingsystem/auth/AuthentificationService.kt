package com.example.ticketbookingsystem.auth

import com.example.ticketbookingsystem.entity.User
import com.example.ticketbookingsystem.repository.UserRepository
import com.example.ticketbookingsystem.util.JwtProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
open class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtProvider: JwtProvider
) {

    /**
     * Метод для регистрации нового пользователя в системе.
     *
     * @param nickname Отображаемое имя (ник) пользователя.
     * @param email Адрес электронной почты пользователя.
     * @param password Пароль пользователя.
     * @return Сохраненный объект [User] с данными зарегистрированного пользователя.
     */
    fun registerUser(nickname: String, email: String, password: String): User {
        // Проверка входных данных
        val emailRegex = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")

        if (nickname.isBlank() || email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Nickname, email, and password cannot be blank")
        }

        if (!emailRegex.matches(email)) {
            throw IllegalArgumentException("Invalid email format")
        }

        if (!passwordRegex.matches(password)) {
            throw IllegalArgumentException("Password must have at least 8 characters, including uppercase, lowercase, digits, and special characters")
        }

        // Хеширование пароля перед сохранением в базу данных
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(nickname = nickname, email = email, password = encodedPassword)
        return userRepository.save(user)
    }

    /**
     * Метод для аутентификации зарегистрированного пользователя и генерации JWT токена.
     *
     * @param email Адрес электронной почты пользователя.
     * @param password Пароль пользователя.
     * @return JWT токен, если аутентификация прошла успешно, иначе null.
     */
    fun authenticate(email: String, password: String): String? {
        val user = userRepository.findByEmail(email) ?: return null
        if (passwordEncoder.matches(password, user.password)) {
            val token = jwtProvider.generateToken(user.id)
            return token
        }
        return null
    }

    /**
     * Метод для получения информации о пользователе по JWT токену.
     *
     * @param token JWT токен пользователя.
     * @return Объект [User], если токен валиден, иначе null.
     */
    @Transactional
    open fun getUserInfo(token: String): User? {
        if (!jwtProvider.validateToken(token)) {
            return null
        }
        val userId = jwtProvider.getUserIdFromToken(token)
        return userRepository.findById(userId).orElse(null)
    }
}