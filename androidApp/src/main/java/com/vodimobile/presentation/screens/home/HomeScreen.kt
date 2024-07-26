package com.vodimobile.presentation.screens.home

import BottomCard
import CarsCard
import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.domain.model.Car
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.LeafHomeScreen
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.screens.home.components.HomeScreenSupBar
import com.vodimobile.presentation.screens.home.components.SnapVodimobileTopAppBar
import com.vodimobile.presentation.screens.home.store.HomeEffect
import com.vodimobile.presentation.screens.home.store.HomeIntent
import com.vodimobile.presentation.screens.home.store.HomeState
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeState: State<HomeState>,
    @SuppressLint("ComposeMutableParameters") homeEffect: MutableSharedFlow<HomeEffect>,
    onHomeIntent: (HomeIntent) -> Unit,
    navHostController: NavHostController,
    selectedDate: Long,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = Unit) {
        homeEffect.collect { effect ->
            when (effect) {
                HomeEffect.FieldClick -> {
                    navHostController.navigate(route = DialogIdentifiers.DATE_SELECT_DIALOG)
                }

                HomeEffect.NotificationButtonClick -> {

                }

                HomeEffect.AllCarsClick -> {
                    navHostController.navigate(route = LeafHomeScreen.ALL_CARS)
                }
            }
        }
    }

    ExtendedTheme {
        Scaffold(
            containerColor = ExtendedTheme.colorScheme.secondaryBackground,
            topBar = {
                SnapVodimobileTopAppBar(
                    date = selectedDate,
                    onNotificationButtonClick = { onHomeIntent(HomeIntent.NotificationButtonClick) },
                    onFieldClick = { onHomeIntent(HomeIntent.FieldClick) },
                    onButtonClick = {}
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .testTag(TestTags.HomeScreen.contentScroll),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 20.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    HomeScreenSupBar(
                        onClick = {
                            onHomeIntent(HomeIntent.AllCarsClick)
                        }
                    )
                }
                itemsIndexed(homeState.value.carList) { _, item: Car ->
                    CarsCard(
                        carItem = item,
                        onBookClick = { carItem -> onHomeIntent(HomeIntent.ShowModal(car = carItem)) }
                    )
                }
            }

            if (homeState.value.showBottomSheet) {
                BottomCard(
                    carItem = homeState.value.selectedCar,
                    onDismiss = { onHomeIntent(HomeIntent.CloseModal) })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        val homeViewModel = HomeViewModel()
        HomeScreen(
            homeState = homeViewModel.homeState.collectAsState(),
            homeEffect = homeViewModel.homeEffect,
            onHomeIntent = homeViewModel::onIntent,
            navHostController = rememberNavController(),
            selectedDate = 0
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeScreenLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        val homeViewModel = HomeViewModel()
        HomeScreen(
            homeState = homeViewModel.homeState.collectAsState(),
            homeEffect = homeViewModel.homeEffect,
            onHomeIntent = homeViewModel::onIntent,
            navHostController = rememberNavController(),
            selectedDate = 0
        )
    }
}