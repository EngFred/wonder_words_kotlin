package com.kotlin.wonderwords.features.profile.presentation.screen

import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

sealed class ProfileUiEvents {
    data object LoggedOut : ProfileUiEvents()
    data object Refreshed : ProfileUiEvents()
}