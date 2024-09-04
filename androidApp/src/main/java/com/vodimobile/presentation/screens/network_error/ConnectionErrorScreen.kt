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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.components.block.ErrorItem
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorEffect
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.internet.ConnectionStatus
import com.vodimobile.presentation.utils.internet.getCurrentConnectivityStatus
import kotlinx.coroutines.flow.MutableSharedFlow


@SuppressLint("ComposeModifierMissing")
@Composable
fun ConnectionErrorScreen(
    onNetworkErrorIntent: (ConnectionErrorIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") networkErrorEffect: MutableSharedFlow<ConnectionErrorEffect>,
    navHostController: NavHostController,
    screen: String
) {
    val context = LocalContext.current
    Scaffold {
        onNetworkErrorIntent(ConnectionErrorIntent.ClickRepeat(value = screen))

        LaunchedEffect(key1 = Unit) {
            networkErrorEffect.collect { effect ->
                when (effect) {
                    is ConnectionErrorEffect.ClickRepeat -> {
                        val isConnected = getCurrentConnectivityStatus(context = context) === ConnectionStatus.Available
                        if (isConnected) {
                            navHostController.navigate(route = screen)
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
                onNetworkErrorIntent = {
                    onNetworkErrorIntent(ConnectionErrorIntent.ClickRepeat(value = screen))
                },
                icon = {
                    Image(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .size(128.dp),
                        painter = painterResource(id = R.drawable.connection_error),
                        contentDescription = stringResource(R.string.connection_error)
                    )
                },
                screen = screen
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
            navHostController = rememberNavController(),
            screen = ""
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
            navHostController = rememberNavController(),
            screen = ""
        )
    }
}
