package com.vodimobile.navigation

import com.vodimobile.android.R
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
        emptyList()
    )

    data object Order : BottomNavItemScreen(
        R.string.orders_screen_label,
        R.drawable.order_icon,
        RootScreen.ORDERS_SCREEN,
        emptyList()
    )

    data object Profile : BottomNavItemScreen(
        R.string.profile_screen_label,
        R.drawable.profile_icon,
        LeafScreen.PROFILE_SCREEN,
        listOf(
            LeafScreen.FAQ_SCREEN,
            LeafScreen.RULES_SCREEN,
            LeafScreen.CONTACTS_SCREEN,
            LeafScreen.RULE_DETAILS_SCREEN
        )
    )
}
