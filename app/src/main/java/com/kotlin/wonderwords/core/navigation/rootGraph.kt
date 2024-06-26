package com.kotlin.wonderwords.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kotlin.wonderwords.core.presentation.SplashScreen
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.auth.presentation.nav.authGraph
import com.kotlin.wonderwords.home.HomeScreen

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    navController: NavHostController
){

    val splash = "splash"
    
    NavHost(navController = navController, route = Graphs.ROOT, startDestination = splash) {
        composable(splash) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Graphs.HOME) {
                        popUpTo(splash) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Graphs.AUTH_GRAPH) {
                        popUpTo(splash) {
                            inclusive = true
                        }
                    }
                }, sharedViewModel = sharedViewModel
            )
        }
        authGraph(navController, modifier, sharedViewModel)
        composable(Graphs.HOME) {
            HomeScreen(
                sharedViewModel = sharedViewModel,
                onSignOut = {
                navController.navigate(Graphs.AUTH_GRAPH){
                    popUpTo(Graphs.HOME){
                        inclusive = true
                    }
                }
            })
        }
    }
}