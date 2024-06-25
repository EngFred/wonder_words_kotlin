package com.kotlin.wonderwords.features.profile.presentation.screen

import com.kotlin.wonderwords.features.profile.domain.entity.ThemeMode

data class ProfileUiState(
    val isLoading: Boolean = true,
    val selectTheme: ThemeMode = ThemeMode.Light
)
