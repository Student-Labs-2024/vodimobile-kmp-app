package com.vodimobile.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vodimobile.navigation.NavGraph
import com.vodimobile.presentation.components.BottomBar
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun Root() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
            if (getIsShowBottomBar(currentRoute))
                BottomBar(navController = navController)
        }
    ) { paddingValues ->

        NavGraph(
            navHostController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

private fun getIsShowBottomBar(currentRoute: String?): Boolean {
    return currentRoute != RootScreen.START_SCREEN &&
            currentRoute != RegistrationScreens.REGISTRATION_SCREEN &&
            currentRoute != RegistrationScreens.USER_AGREE_SCREEN &&
            currentRoute != RegistrationScreens.SMS_VERIFY &&
            currentRoute != RegistrationScreens.START_SCREEN &&
            currentRoute != LeafHomeScreen.NO_INTERNET_SCREEN &&
            currentRoute != LeafHomeScreen.SERVER_ERROR_SCREEN &&
            currentRoute != LeafScreen.CHANGE_PASSWORD_SCREEN &&
            currentRoute != LeafHomeScreen.ALL_CARS
}

@Preview
@Composable
private fun RootPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(color = MaterialTheme.colorScheme.onPrimary) {
            Root()
        }
    }
}
