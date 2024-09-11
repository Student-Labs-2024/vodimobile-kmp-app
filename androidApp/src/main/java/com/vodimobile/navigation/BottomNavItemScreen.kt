package com.vodimobile.navigation

import com.vodimobile.android.R
import com.vodimobile.presentation.LeafHomeScreen
import com.vodimobile.presentation.LeafOrdersScreen
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.RootScreen

sealed class BottomNavItemScreen(
    val title: Int,
    val iconId: Int,
    val route: String,
    val list: List<String>
) {
    data object Home : BottomNavItemScreen(
        R.string.home_screen_label,
        R.drawable.home_icon,
        RootScreen.HOME_SCREEN,
        listOf(
            LeafHomeScreen.HOME_SCREEN,
            LeafHomeScreen.ALL_CARS
        )
    )

    data object Order : BottomNavItemScreen(
        R.string.orders_screen_label,
        R.drawable.order_icon,
        RootScreen.ORDERS_SCREEN,
        listOf(LeafOrdersScreen.ORDERS_SCREEN)
    )

    data object Profile : BottomNavItemScreen(
        R.string.profile_screen_label,
        R.drawable.profile_icon,
        LeafScreen.PROFILE_SCREEN,
        listOf(
            LeafScreen.FAQ_SCREEN,
            LeafScreen.RULES_SCREEN,
            LeafScreen.CONTACTS_SCREEN,
            LeafScreen.RULE_DETAILS_SCREEN,
            LeafScreen.EDIT_PROFILE
        )
    )
}
