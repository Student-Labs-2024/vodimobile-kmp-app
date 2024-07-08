package com.vodimobile.navigation

import com.vodimobile.R


sealed class BottomNavItemScreen(
    val title: Int,
    val iconId: Int,
    val route: String
) {
    object Home : BottomNavItemScreen(R.string.home_screen_label, R.drawable.home_icon, "home")

    object Order: BottomNavItemScreen(R.string.orders_screen_label, R.drawable.order_icon, "order")

    object Profile: BottomNavItemScreen(R.string.profile_screen_label, R.drawable.profile_icon, "profile")

}