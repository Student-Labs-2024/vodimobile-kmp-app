package com.vodimobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vodimobile.presentation.Routing
import com.vodimobile.presentation.screens.home.HomeScreen
import com.vodimobile.presentation.screens.orders.OrdersScreen
import com.vodimobile.presentation.screens.profile.ProfileScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Routing.HOME_SCREEN
    ) {
        composable(Routing.HOME_SCREEN) {
            HomeScreen()
        }
        composable(Routing.ORDERS_SCREEN) {
            OrdersScreen()
        }
        composable(Routing.PROFILE_SCREEN) {
            ProfileScreen()
        }
    }
}