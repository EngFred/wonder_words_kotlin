package com.kotlin.wonderwords.features.user_update.presentation.screens

import com.kotlin.wonderwords.features.user_update.domain.models.ProfilePicSource

sealed class UpdateUserUiEvents {
    data class UsernameChanged(val name: String) : UpdateUserUiEvents()
    data class EmailChanged(val email: String) : UpdateUserUiEvents()
    data class PasswordChanged(val password: String) : UpdateUserUiEvents()
    data class TwitterUsernameChanged(val twitterUsername: String) : UpdateUserUiEvents()
    data class FacebookUsernameChanged(val facebookUsername: String) : UpdateUserUiEvents()
    data class ProfilePicSourceChanged( val source: ProfilePicSource ) : UpdateUserUiEvents()

    data object UpdateButtonClicked : UpdateUserUiEvents()

    data object SelectSourceButtonClicked : UpdateUserUiEvents()
}