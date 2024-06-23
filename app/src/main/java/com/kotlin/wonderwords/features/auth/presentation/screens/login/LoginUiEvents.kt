package com.kotlin.wonderwords.features.auth.presentation.screens.login

sealed class LoginUiEvents {
    data object LoginButtonClicked: LoginUiEvents()
    data class LoginChanged(val login: String) : LoginUiEvents()
    data class PasswordChanged(val password: String) : LoginUiEvents()
}