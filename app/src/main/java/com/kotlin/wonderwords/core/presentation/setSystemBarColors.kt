package com.kotlin.wonderwords.core.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetSystemBarColor(
    barColor: Color,
    isAuth: Boolean = true
) {
    val view = LocalView.current

    LaunchedEffect(key1 = true) {
        val window = (view.context as Activity).window
        window.statusBarColor = barColor.toArgb()
        window.navigationBarColor = barColor.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isAuth
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
    }

}