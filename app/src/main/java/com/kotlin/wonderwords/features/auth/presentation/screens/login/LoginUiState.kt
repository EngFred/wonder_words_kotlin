package com.kotlin.wonderwords.features.auth.presentation.screens.login

data class LoginUiState(
    val login: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val loginError: String? = null,
    val logInSuccess: Boolean = false,
    val isFormValid: Boolean = false,
)