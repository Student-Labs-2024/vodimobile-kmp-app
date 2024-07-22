package com.vodimobile.presentation.screens.network_error

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.components.ErrorItem
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorEffect
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun ConnectionErrorScreen(
    onNetworkErrorIntent: (ConnectionErrorIntent) -> Unit,
    networkErrorEffect: MutableSharedFlow<ConnectionErrorEffect>,
    navHostController: NavHostController
) {
    LaunchedEffect(key1 = Unit) {
        networkErrorEffect.collect() { effect ->
            when (effect) {
                ConnectionErrorEffect.CloseRepetir -> {}
            }
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 40.dp)
            .padding(top = 20.dp, bottom = 20.dp),
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
                contentDescription = stringResource(R.string.connection_error)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkErrorScreen() {
    VodimobileTheme(darkTheme = false,dynamicColor = false) {
        val connectionErrorViewModel = ConnectionErrorViewModel()
        ConnectionErrorScreen(
            onNetworkErrorIntent = connectionErrorViewModel::onIntent,
            networkErrorEffect = connectionErrorViewModel.connectionErrorEffect,
            navHostController = rememberNavController()
        )
    }
}


