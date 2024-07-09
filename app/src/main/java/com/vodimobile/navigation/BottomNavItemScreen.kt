package com.vodimobile.navigation

import com.vodimobile.R
import com.vodimobile.presentation.Routing


sealed class BottomNavItemScreen(
    val title: Int,
    val iconId: Int,
    val route: String
) {
    object Home : BottomNavItemScreen(R.string.home_screen_label, R.drawable.home_icon, Routing.HOME_SCREEN)

    object Order: BottomNavItemScreen(R.string.orders_screen_label, R.drawable.order_icon, Routing.ORDERS_SCREEN)

    object Profile: BottomNavItemScreen(R.string.profile_screen_label, R.drawable.profile_icon, Routing.PROFILE_SCREEN)

}