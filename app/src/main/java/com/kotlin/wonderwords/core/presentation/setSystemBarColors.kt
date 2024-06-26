package com.kotlin.wonderwords.core.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.kotlin.wonderwords.core.presentation.theme.DarkSlateGrey
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun SetSystemBarColor(
    barColor: Color? = null,
    isAuth: Boolean,
    sharedViewModel: SharedViewModel
) {
    val view = LocalView.current

    val currentTheme = sharedViewModel.currentTheme.collectAsState().value

    val statusBarColor = if(!isAuth) {
        barColor?.toArgb() ?: MaterialTheme.colorScheme.surface.toArgb()
    } else {
        if ( currentTheme == ThemeMode.Dark || isSystemInDarkTheme() ) {
            DarkSlateGrey.toArgb()
        } else {
            Color.Black.toArgb()
        }
    }
    val appearance = if(!isAuth) {
        if (currentTheme != ThemeMode.System) {
            currentTheme != ThemeMode.Dark
        } else {
            !isSystemInDarkTheme()
        }
    } else {
        false
    }

    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = statusBarColor
        window.navigationBarColor = statusBarColor
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = appearance
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
    }

}