package com.kotlin.wonderwords.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kotlin.wonderwords.core.navigation.DetailRoutes
import com.kotlin.wonderwords.core.navigation.Graphs
import com.kotlin.wonderwords.features.details.presentation.screen.QuoteDetailsScreen

fun NavGraphBuilder.detailGraph(navController: NavHostController) {
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
            QuoteDetailsScreen(quoteId = quoteId)
        }
    }
}