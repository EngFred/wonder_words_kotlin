package com.kotlin.wonderwords.features.auth.presentation.screens.signup

data class SignupUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmedPassword: String = "",
    val isLoading: Boolean = false,
    val signupError: String? = null,
    val signupSuccess: Boolean = false,
    val isFormValid: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val usernameError: String? = null,
)
