package com.kotlin.wonderwords.features.auth.presentation.screens.forgot_password

sealed class ForgotPasswordUiEvents {
    data object SubmitButtonClicked : ForgotPasswordUiEvents()
    data class EmailChanged(val email: String) : ForgotPasswordUiEvents()
}