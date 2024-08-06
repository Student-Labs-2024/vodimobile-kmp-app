package com.vodimobile.presentation.screens.network_error

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.LeafHomeScreen
import com.vodimobile.presentation.components.ErrorItem
import com.vodimobile.presentation.store.ConnectionErrorEffect
import com.vodimobile.presentation.store.ConnectionErrorIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.internet.ConnectionStatus
import com.vodimobile.presentation.utils.internet.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow


@SuppressLint("ComposeModifierMissing")
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ConnectionErrorScreen(
    onNetworkErrorIntent: (ConnectionErrorIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") networkErrorEffect: MutableSharedFlow<ConnectionErrorEffect>,
    navHostController: NavHostController
) {
    Scaffold {
        val connection by connectivityState()

        onNetworkErrorIntent(ConnectionErrorIntent.ClickRepeat)

        LaunchedEffect(key1 = Unit) {
            networkErrorEffect.collect { effect ->
                when (effect) {
                    ConnectionErrorEffect.ClickRepeat -> {
                        val isConnected = connection === ConnectionStatus.Available
                        if (isConnected) {
                            navHostController.clearBackStack(route = LeafHomeScreen.NO_INTERNET_SCREEN)
                            navHostController.navigate(route = LeafHomeScreen.HOME_SCREEN)
                        }
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 60.dp)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            ErrorItem(
                title = stringResource(R.string.connection_error),
                subtitle = stringResource(R.string.connection_error_subtitle),
                onNetworkErrorIntent = onNetworkErrorIntent,
                icon = {
                    Image(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .size(128.dp),
                        painter = painterResource(id = R.drawable.connection_error),
                        contentDescription = stringResource(R.string.connection_error)
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun NetworkErrorScreenLight() {
    VodimobileTheme(darkTheme = false, dynamicColor = false) {
        val connectionErrorViewModel = ConnectionErrorViewModel()
        ConnectionErrorScreen(
            onNetworkErrorIntent = connectionErrorViewModel::onIntent,
            networkErrorEffect = connectionErrorViewModel.connectionErrorEffect,
            navHostController = rememberNavController()
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NetworkErrorScreenNight() {
    VodimobileTheme(dynamicColor = false) {
        val connectionErrorViewModel = ConnectionErrorViewModel()
        ConnectionErrorScreen(
            onNetworkErrorIntent = connectionErrorViewModel::onIntent,
            networkErrorEffect = connectionErrorViewModel.connectionErrorEffect,
            navHostController = rememberNavController()
        )
    }
}
