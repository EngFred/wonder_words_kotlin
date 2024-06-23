package com.kotlin.wonderwords.features.auth.presentation.screens.signup

sealed class SignupUiEvents {
    data object SignupButtonClicked: SignupUiEvents()
    data class NameChanged(val name: String) : SignupUiEvents()
    data class EmailChanged(val email: String) : SignupUiEvents()
    data class PasswordChanged(val password: String) : SignupUiEvents()
    data class ConfirmedPasswordChanged(val confirmedPassword: String) : SignupUiEvents()
}