package com.example.ticketbookingsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * Класс конфигурации безопасности веб-приложения.
 * Настраивает защиту от межсайтовой подделки запросов (CSRF), авторизацию запросов
 * и создает бин для хеширования паролей пользователей.
 */
@Configuration
@EnableWebSecurity
open class SecurityConfig : WebSecurityConfigurerAdapter() {

    /**
     * Создает бин для хеширования паролей пользователей с использованием алгоритма BCrypt.
     *
     * @return Экземпляр [BCryptPasswordEncoder].
     */
    @Bean
    open fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * Настраивает безопасность веб-приложения.
     *
     * @param http Объект [HttpSecurity] для настройки безопасности.
     */
    override fun configure(http: HttpSecurity) {
        http
            // Отключение защиты от межсайтовой подделки запросов (CSRF)
            .csrf().disable()
            // Настройка авторизации запросов
            .authorizeRequests()
            // Разрешение доступа ко всем запросам `/api/users/**` без аутентификации
            .antMatchers("/api/users/**").permitAll()
            // Требование аутентификации для всех остальных запросов
            .anyRequest().authenticated()
    }
}