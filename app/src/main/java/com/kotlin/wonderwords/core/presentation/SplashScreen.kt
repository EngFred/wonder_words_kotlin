package com.kotlin.wonderwords.core.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    sharedViewModel: SharedViewModel
) {

    SetSystemBarColor(sharedViewModel = sharedViewModel, isAuth = false, barColor = Color.White)

    val userStatus = sharedViewModel.userStatus.collectAsState().value

    LaunchedEffect(userStatus) {
        if (userStatus != null) {
            if (userStatus) {
                Log.d("#", "navigate to home")
                onNavigateToHome()
            } else {
                Log.d("#", "navigate to login")
                onNavigateToLogin()
            }
        }
        Log.d("#", "User status ::: $userStatus")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...", fontSize = 22.sp, color = Color.Black)
//        Image(painter = painterResource(id = R.drawable.logo_flutter), contentDescription = null, Modifier.size(170.dp) )
    }
}