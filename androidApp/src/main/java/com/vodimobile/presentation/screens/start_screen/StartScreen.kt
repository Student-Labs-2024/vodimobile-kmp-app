package com.vodimobile.presentation.screens.start_screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.RegistrationScreens
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.components.SecondaryButton
import com.vodimobile.presentation.screens.start_screen.store.StartScreenEffect
import com.vodimobile.presentation.screens.start_screen.store.StartScreenIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow

@SuppressLint("ComposeModifierMissing")
@Composable
fun StartScreen(
    onStartScreenIntent: (StartScreenIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") startScreenEffect: MutableSharedFlow<StartScreenEffect>,
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        startScreenEffect.collect { effect ->
            when (effect) {
                StartScreenEffect.CloseClick -> {
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        "no-auth",
                        false,
                    )
                    navHostController.navigateUp()
                }
                StartScreenEffect.ClickLogin -> navHostController.navigate(RegistrationScreens.AUTHORIZATION_SCREEN)
                StartScreenEffect.ClickRegistration -> {
                    navHostController.navigate(route = RegistrationScreens.REGISTRATION_SCREEN)
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 20.dp, alignment = Alignment.Top)
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = {
                    onStartScreenIntent(StartScreenIntent.CloseClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(id = R.string.close_button_content_description)
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 60.dp)
                .size(128.dp)
        )
        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.login_or_create_title),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = R.string.login_or_create_subtitle),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        PrimaryButton(
            text = stringResource(id = R.string.requister_str),
            enabled = true,
            onClick = {
                onStartScreenIntent(StartScreenIntent.ClickRegistration)
            })
        SecondaryButton(
            text = stringResource(id = R.string.login_str),
            enabled = true,
            onClick = {
                onStartScreenIntent(StartScreenIntent.ClickLogin)
            })
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun StartScreenLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val startScreenViewModel = StartScreenViewModel()
            StartScreen(
                onStartScreenIntent = startScreenViewModel::onIntent,
                startScreenEffect = startScreenViewModel.startScreenEffect,
                navHostController = rememberNavController()
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StartScreenDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val startScreenViewModel = StartScreenViewModel()
            StartScreen(
                onStartScreenIntent = startScreenViewModel::onIntent,
                startScreenEffect = startScreenViewModel.startScreenEffect,
                navHostController = rememberNavController()
            )
        }
    }
}
