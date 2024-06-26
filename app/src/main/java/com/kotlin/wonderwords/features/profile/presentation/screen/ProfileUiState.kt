package com.kotlin.wonderwords.features.profile.presentation.screen

import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode
import com.kotlin.wonderwords.features.profile.domain.model.UserProfileDetails

data class ProfileUiState(
    val isLoading: Boolean = true,
    val selectTheme: ThemeMode = ThemeMode.Light,
    val error: String? = null,
    val user: UserProfileDetails? = null,
    val signingOut: Boolean = false,
    val signOutError: String? = null,
    val signOutSuccess: Boolean = false
)
