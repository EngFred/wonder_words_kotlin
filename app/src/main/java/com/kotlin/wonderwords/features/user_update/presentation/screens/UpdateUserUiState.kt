package com.kotlin.wonderwords.features.user_update.presentation.screens

import com.kotlin.wonderwords.features.user_update.domain.models.ProfilePicSource

data class UpdateUserUiState(
    val username: String? = null,
    val usernameError: String? = null,
    val email: String? = null,
    val emailError: String? = null,
    val password: String? = null,
    val passwordError: String? = null,
    val twitterUsername: String? = null,
    val facebookUsername: String? = null,
    val profilePicSource: ProfilePicSource? = null,
    val isUpdating: Boolean = false,
    val updateError: String? = null,
    val updateSuccess: Boolean = false,
    val isFormValid: Boolean = false,
    val expanded: Boolean = false
)
