package com.kotlin.wonderwords.features.profile.presentation.screen

import com.kotlin.wonderwords.features.profile.domain.entity.ThemeMode

sealed class ProfileUiEvents {
    data class ThemeChange( val theme: ThemeMode ) : ProfileUiEvents()
}