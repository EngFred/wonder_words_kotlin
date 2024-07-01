package com.kotlin.wonderwords.features.auth.domain.models

data class AuthRequest(
    val user: User
)

data class User(
    val login: String? = null,
    val email: String? = null,
    val password: String? = null,
    val reset_password_token: String? = null
)
