package com.vodimobile.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vodimobile.navigation.BottomNavItemScreen
import com.vodimobile.presentation.BottomAppBarAlpha


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val navigationItems = listOf(
        BottomNavItemScreen.Home,
        BottomNavItemScreen.Order,
        BottomNavItemScreen.Profile
    )
    Column(modifier = Modifier) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground.copy(BottomAppBarAlpha.BACKGROUND_ALPHA),
            thickness = 1.dp
        )
        BottomNavigation(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(), MaterialTheme.colorScheme.onPrimary
        ) {
            val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route

            navigationItems.forEach { item->
                BottomNavigationItem(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    selected = currentRoute == item.route || item.list.any { subitem -> currentRoute == subitem },
                    onClick = {
                        navController.navigate(item.route)
                    },
                    icon = {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .fillMaxWidth(),
                            painter = painterResource(id = item.iconId),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.title ),
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onBackground.copy(BottomAppBarAlpha.BACKGROUND_ALPHA)
                    )
            }
        }
    }
}


