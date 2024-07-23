package com.vodimobile.presentation.screens.network_error

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.LeafScreen
import com.vodimobile.presentation.components.ErrorItem
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorEffect
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.ConnectionStatus
import com.vodimobile.presentation.utils.connectivityState
import kotlinx.coroutines.flow.MutableSharedFlow


@Composable
fun ConnectionErrorScreen(
    onNetworkErrorIntent: (ConnectionErrorIntent) -> Unit,
    networkErrorEffect: MutableSharedFlow<ConnectionErrorEffect>,
    navHostController: NavHostController
) {
    Scaffold {
        val connection by connectivityState()
        val isConnected = connection === ConnectionStatus.Available

        LaunchedEffect(key1 = Unit) {
            networkErrorEffect.collect() { effect ->
                when (effect) {
                    ConnectionErrorEffect.CloseRepetir -> {
                        if (isConnected) {

                        } else {
                        }
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 60.dp)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            ErrorItem(
                title = stringResource(R.string.connection_error),
                subtitle = stringResource(R.string.connection_error_subtitle),
            ) {
                Image(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .size(128.dp),
                    painter = painterResource(id = R.drawable.nointernet),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    contentDescription = stringResource(R.string.connection_error)
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun NetworkErrorScreenLight() {
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
fun NetworkErrorScreenNight() {
    VodimobileTheme(dynamicColor = false) {
        val connectionErrorViewModel = ConnectionErrorViewModel()
        ConnectionErrorScreen(
            onNetworkErrorIntent = connectionErrorViewModel::onIntent,
            networkErrorEffect = connectionErrorViewModel.connectionErrorEffect,
            navHostController = rememberNavController()
        )
    }
}






