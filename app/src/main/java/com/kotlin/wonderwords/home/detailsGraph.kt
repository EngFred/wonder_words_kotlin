package com.kotlin.wonderwords.home

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
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
import com.kotlin.wonderwords.features.create_quote.presentation.screens.CreateQuoteScreen
import com.kotlin.wonderwords.features.details.presentation.screen.QuoteDetailsScreen
import com.kotlin.wonderwords.features.user_update.presentation.screens.UpdateUserScreen

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
            route = "${DetailRoutes.UpdateProfile.destination}/{username}/{email}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                },
                navArgument("email") {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally()
            }
        ) { navBackStackEntry ->

            val username = navBackStackEntry.arguments?.getString("username")!!
            val email = navBackStackEntry.arguments?.getString("email")!!
            UpdateUserScreen(
                modifier = modifier,
                sharedViewModel = sharedViewModel,
                onUpdateSuccess = {
                    navController.popBackStack()
                },
                username = username,
                email = email
            )
        }

        composable(
            route = "${DetailRoutes.CreateQuote.destination}/{username}",
            arguments = listOf(
                navArgument(
                    name = "username"
                ) {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInVertically()
            }
        ) { navBackStackEntry ->
            val username = navBackStackEntry.arguments?.getString("username") ?: "JohnDoe"
            CreateQuoteScreen(
                modifier = modifier,
                sharedViewModel = sharedViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onQuotedCreatedSuccessfully = { quoteId ->
                    navController.navigate("${DetailRoutes.QuoteDetails.destination}/$quoteId"){
                        launchSingleTop = true
                        popUpTo(DetailRoutes.QuoteDetails.destination){
                            inclusive = true
                        }
                    }
                },
                username = username
            )
        }
    }
}