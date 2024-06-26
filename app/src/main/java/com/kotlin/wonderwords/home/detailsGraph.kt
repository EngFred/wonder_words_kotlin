package com.kotlin.wonderwords.home

import androidx.compose.animation.slideInHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kotlin.wonderwords.core.navigation.DetailRoutes
import com.kotlin.wonderwords.core.navigation.Graphs
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.details.presentation.screen.QuoteDetailsScreen
import com.kotlin.wonderwords.features.profile.presentation.screen.UpdateProfileScreen

fun NavGraphBuilder.detailGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    modifier: Modifier = Modifier
) {
    navigation(startDestination = DetailRoutes.QuoteDetails.destination, route = Graphs.DETAILS) {
        composable(
            route = "${DetailRoutes.QuoteDetails.destination}/{quoteId}",
            arguments = listOf(
                navArgument("quoteId") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val quoteId = navBackStackEntry.arguments?.getInt("quoteId") ?: -1
            QuoteDetailsScreen(quoteId = quoteId, modifier = modifier, onBack = {
                navController.popBackStack()
            }, sharedViewModel = sharedViewModel)
        }

        composable(
            route = DetailRoutes.UpdateProfile.destination,
            enterTransition = {
                slideInHorizontally()
            }
        ) {
            UpdateProfileScreen(modifier, sharedViewModel = sharedViewModel)
        }
    }
}