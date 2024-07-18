package com.vodimobile.navigation

import com.vodimobile.android.R
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.RootScreen

sealed class BottomNavItemScreen(
    val title: Int,
    val iconId: Int,
    val route: String
) {
    data object Home : BottomNavItemScreen(R.string.home_screen_label, R.drawable.home_icon, RootScreen.HOME_SCREEN)

    data object Order: BottomNavItemScreen(R.string.orders_screen_label, R.drawable.order_icon, RootScreen.ORDERS_SCREEN)

    data object Profile: BottomNavItemScreen(R.string.profile_screen_label, R.drawable.profile_icon, LeafScreen.PROFILE_SCREEN)
}
