package com.kotlin.wonderwords.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kotlin.wonderwords.core.navigation.DetailRoutes
import com.kotlin.wonderwords.core.navigation.Graphs
import com.kotlin.wonderwords.features.details.presentation.screen.QuoteDetailsScreen

fun NavGraphBuilder.detailGraph(navController: NavHostController) {
    navigation(startDestination = DetailRoutes.QuoteDetails.destination, route = Graphs.DETAILS) {
        composable(DetailRoutes.QuoteDetails.destination) {
            QuoteDetailsScreen()
        }
    }
}