package com.vodimobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vodimobile.presentation.screens.home.HomeScreen
import com.vodimobile.presentation.screens.orders.OrdersScreen
import com.vodimobile.presentation.screens.profile.ProfileScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "home"
    ) {
        composable("home" ) {
            HomeScreen()
        }
        composable("order") {
            OrdersScreen()
        }
        composable("profile") {
            ProfileScreen()
        }
    }
}