package com.kotlin.wonderwords.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavHostController) {

    val bottomBarNavItemsLists = listOf(
        BottomBarNavItem(route = BottomBarNavRoutes.Quotes.destination, icon = Icons.Default.FormatQuote, label = "Quotes"),
        BottomBarNavItem(route = BottomBarNavRoutes.Profile.destination, icon = Icons.Default.Person, label = "Profile")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = bottomBarNavItemsLists.any { it.route == currentDestination?.route }

    if (showBottomBar) {
        NavigationBar(
            containerColor = Color.Transparent,
        ) {
            bottomBarNavItemsLists.forEach{ navItem ->
                NavigationBarItem(
                    selected = navItem.route == currentDestination?.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id){ saveState =  true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label) },
                    label = { Text(text = navItem.label) }
                )
            }
        }
    }

}