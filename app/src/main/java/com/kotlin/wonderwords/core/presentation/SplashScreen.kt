package com.kotlin.wonderwords.core.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kotlin.wonderwords.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    SetSystemBarColor(barColor = MaterialTheme.colorScheme.surface)

    val token = splashViewModel.userToken.collectAsState().value

    LaunchedEffect(token) {
        if (token != null) {
            if (token.isNotEmpty()) {
                Log.d("#", "navigate to home")
                onNavigateToHome()
            } else {
                Log.d("#", "navigate to login")
                onNavigateToLogin()
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.drawable.logo_flutter), contentDescription = null, Modifier.size(170.dp) )
    }
}