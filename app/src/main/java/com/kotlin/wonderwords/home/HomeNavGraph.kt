package com.kotlin.wonderwords.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kotlin.wonderwords.core.navigation.DetailRoutes
import com.kotlin.wonderwords.core.navigation.Graphs
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.profile.presentation.screen.ProfileScreen
import com.kotlin.wonderwords.features.quotes.presentation.screen.QuotesScreen

@Composable
fun HomeNavGraph(
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel,
    navController: NavHostController,
    onSignOut: () -> Unit
) {
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
                modifier = modifier,
                sharedViewModel = sharedViewModel
            )
        }
        composable(BottomBarNavRoutes.Profile.destination) {
            ProfileScreen(
                modifier,
                onUpdateProfile = { username, email ->
                    navController.navigate("${DetailRoutes.UpdateProfile.destination}/$username/$email") {
                        launchSingleTop = true
                    }
                },
                sharedViewModel = sharedViewModel,
                onSignOut = onSignOut
            )
        }

        detailGraph(navController = navController, modifier = modifier, sharedViewModel = sharedViewModel)
    }
}

