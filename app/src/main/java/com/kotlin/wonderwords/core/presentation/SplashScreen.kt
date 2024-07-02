package com.kotlin.wonderwords.core.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kotlin.wonderwords.R
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    sharedViewModel: SharedViewModel
) {

    SetSystemBarColor(sharedViewModel = sharedViewModel, isAuth = false, barColor = Color.White)

    val userToken = sharedViewModel.userToken.collectAsState().value

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_lottie))

    LaunchedEffect(userToken) {
        if (userToken != null) {
            if (userToken.isNotEmpty()) {
                Log.d("#", "navigate to home")
                onNavigateToHome()
            } else {
                Log.d("#", "navigate to login")
                onNavigateToLogin()
            }
        }
        Log.d("#", "User status ::: $userToken")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            modifier = Modifier.size(95.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            reverseOnRepeat = true
        )

    }
}