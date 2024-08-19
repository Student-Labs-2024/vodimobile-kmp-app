package com.vodimobile.presentation.screens.server_error

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


@SuppressLint("ComposeModifierMissing")
@Composable
fun ServerErrorScreen(
    onServerErrorIntent: (ConnectionErrorIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") serverErrorEffect: MutableSharedFlow<ConnectionErrorEffect>,
    navHostController: NavHostController,
    screen: String
) {
    Scaffold {

        LaunchedEffect(key1 = Unit) {
            serverErrorEffect.collect { effect ->
                when (effect) {
                    is ConnectionErrorEffect.ClickRepeat -> {}
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
                title = stringResource(R.string.server_error),
                subtitle = stringResource(R.string.server_error_subtitle),
                onNetworkErrorIntent = onServerErrorIntent,
                icon = {
                    Image(
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .size(128.dp),
                        painter = painterResource(id = R.drawable.server_error),
                        contentDescription = stringResource(R.string.server_error)
                    )
                },
                screen = screen
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ServerErrorScreenLight() {
    VodimobileTheme(darkTheme = false, dynamicColor = false) {
        val serverErrorViewModel = ServerErrorViewModel()
        ServerErrorScreen(
            onServerErrorIntent = serverErrorViewModel::onIntent,
            serverErrorEffect = serverErrorViewModel.serverErrorEffect,
            navHostController = rememberNavController(),
            screen = ""
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ServerErrorScreenNight() {
    VodimobileTheme(dynamicColor = false) {
        val serverErrorViewModel = ServerErrorViewModel()
        ServerErrorScreen(
            onServerErrorIntent = serverErrorViewModel::onIntent,
            serverErrorEffect = serverErrorViewModel.serverErrorEffect,
            navHostController = rememberNavController(),
            screen = ""
        )
    }
}
