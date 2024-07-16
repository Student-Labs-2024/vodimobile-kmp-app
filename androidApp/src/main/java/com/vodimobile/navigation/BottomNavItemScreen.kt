package com.vodimobile.navigation

import com.vodimobile.android.R
import com.vodimobile.presentation.RootScreen

sealed class BottomNavItemScreen(
    val title: Int,
    val iconId: Int,
    val route: String
) {
    object Home : BottomNavItemScreen(R.string.home_screen_label, R.drawable.home_icon, RootScreen.HOME_SCREEN)

    object Order: BottomNavItemScreen(R.string.orders_screen_label, R.drawable.order_icon, RootScreen.ORDERS_SCREEN)

    object Profile: BottomNavItemScreen(R.string.profile_screen_label, R.drawable.profile_icon, RootScreen.PROFILE_SCREEN)
}
