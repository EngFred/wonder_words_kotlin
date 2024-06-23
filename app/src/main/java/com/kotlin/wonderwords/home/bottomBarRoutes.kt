package com.kotlin.wonderwords.home

sealed class BottomBarNavRoutes(val destination: String) {
    data object Quotes : BottomBarNavRoutes(destination = "quotes")
    data object Profile : BottomBarNavRoutes(destination = "profile")
}