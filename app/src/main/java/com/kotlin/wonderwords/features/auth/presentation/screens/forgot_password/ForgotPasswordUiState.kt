package com.kotlin.wonderwords.features.auth.presentation.screens.forgot_password

data class ForgotPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val sentResetLink: Boolean = false,
    val emailError: String? = null,
    val isFormValid: Boolean = false,
)
