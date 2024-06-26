package com.kotlin.wonderwords.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kotlin.wonderwords.core.navigation.DetailRoutes
import com.kotlin.wonderwords.core.navigation.Graphs
import com.kotlin.wonderwords.features.profile.presentation.screen.ProfileScreen
import com.kotlin.wonderwords.features.quotes.presentation.screen.QuotesScreen

@Composable
fun HomeNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graphs.HOME,
        startDestination = BottomBarNavRoutes.Quotes.destination
    ) {
        composable(BottomBarNavRoutes.Quotes.destination) {
            QuotesScreen(
                onQuoteClick = {
                    navController.navigate("${DetailRoutes.QuoteDetails.destination}/$it") {
                        launchSingleTop = true
                    }
                },
                modifier = modifier
            )
        }
        composable(BottomBarNavRoutes.Profile.destination) {
            ProfileScreen(modifier, onUpdateProfile = {
                navController.navigate(DetailRoutes.UpdateProfile.destination){
                    launchSingleTop = true
                }
            })
        }

        detailGraph(navController, modifier)
    }
}