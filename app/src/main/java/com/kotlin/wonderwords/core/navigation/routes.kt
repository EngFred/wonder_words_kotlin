package com.kotlin.wonderwords.core.navigation

sealed class AuthRoutes(val destination: String ) {
    data object Login : AuthRoutes(destination = "login")
    data object Signup : AuthRoutes(destination = "signup")
    data object ForgotPassword : AuthRoutes(destination = "forgot_password")
}

sealed class DetailRoutes(val destination: String ) {
    data object QuoteDetails : AuthRoutes(destination = "quote_details")
}