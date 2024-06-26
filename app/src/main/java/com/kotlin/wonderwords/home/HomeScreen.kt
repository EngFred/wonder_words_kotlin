package com.kotlin.wonderwords.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.profile.domain.model.ThemeMode

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    onSignOut: () -> Unit,
    navController: NavHostController = rememberNavController()
) {

    SetSystemBarColor(isAuth = false, sharedViewModel = sharedViewModel)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        HomeNavGraph(
            navController = navController,
            sharedViewModel = sharedViewModel,
            modifier = Modifier.padding(innerPadding),
            onSignOut = onSignOut
        )
    }

}
