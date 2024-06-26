package com.kotlin.wonderwords.features.auth.presentation.nav

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kotlin.wonderwords.core.navigation.Graphs
import com.kotlin.wonderwords.core.navigation.AuthRoutes
import com.kotlin.wonderwords.core.presentation.viewmodel.SharedViewModel
import com.kotlin.wonderwords.features.auth.presentation.screens.forgot_password.ForgotPasswordScreen
import com.kotlin.wonderwords.features.auth.presentation.screens.login.LoginScreen
import com.kotlin.wonderwords.features.auth.presentation.screens.signup.SignupScreen

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    modifier: Modifier,
    sharedViewModel: SharedViewModel
) {
    navigation(
        startDestination = AuthRoutes.Login.destination,
        route = Graphs.AUTH_GRAPH
    ) {

        composable(AuthRoutes.Login.destination) {
            LoginScreen(
                onSignup = {
                    navController.navigate(AuthRoutes.Signup.destination) {
                        launchSingleTop = true
                    }
                },
                onLogin = {
                    navController.navigate(Graphs.HOME) {
                        launchSingleTop = true
                        popUpTo(Graphs.AUTH_GRAPH) {
                            inclusive = true
                        }
                    }
                },
                onForgotPassword = {
                    navController.navigate(AuthRoutes.ForgotPassword.destination) {
                        launchSingleTop = true
                    }
                },
                modifier = modifier,
                sharedViewModel = sharedViewModel
            )
        }

        composable(AuthRoutes.Signup.destination) {
            SignupScreen(
                modifier = modifier,
                onLogin = {
                    navController.navigate(AuthRoutes.Login.destination) {
                        launchSingleTop = true
                    }
                },
                onSignupSuccess = {
                    navController.navigate(Graphs.HOME) {
                        launchSingleTop = true
                        popUpTo(Graphs.AUTH_GRAPH) {
                            inclusive = true
                        }
                    }
                },
                sharedViewModel = sharedViewModel
            )
        }

        composable(AuthRoutes.ForgotPassword.destination) {
            ForgotPasswordScreen(
                modifier = modifier,
                onLogin = {
                    navController.navigate(AuthRoutes.Login.destination) {
                        launchSingleTop = true
                    }
                },
                sharedViewModel = sharedViewModel
            )
        }

    }
}