package com.kotlin.wonderwords.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kotlin.wonderwords.core.presentation.SetSystemBarColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    SetSystemBarColor(barColor = MaterialTheme.colorScheme.background, isAuth = false)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        HomeNavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
    }

}
