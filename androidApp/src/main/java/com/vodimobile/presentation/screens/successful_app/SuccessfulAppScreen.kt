package com.vodimobile.presentation.screens.successful_app

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.screens.successful_app.store.SuccessfulEffect
import com.vodimobile.presentation.screens.successful_app.store.SuccessfulIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.LeafHomeScreen
import com.vodimobile.presentation.LeafOrdersScreen
import com.vodimobile.presentation.components.PrimaryButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ComposeModifierMissing")
@Composable
fun SuccessfulAppScreen(
    onSuccessfulIntent: (SuccessfulIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") successfulEffect: MutableSharedFlow<SuccessfulEffect>,
    navHostController: NavHostController
) {
    LaunchedEffect(key1 = Unit) {
        successfulEffect.collect { effect ->
            when (effect) {
                SuccessfulEffect.ClickOrders -> {
                    navHostController.navigate(route = LeafOrdersScreen.ORDERS_SCREEN)
                }

                SuccessfulEffect.CloseClick -> {
                    navHostController.navigate(route = LeafHomeScreen.HOME_SCREEN)
                }
            }
        }
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 12.dp)
            ) {
                IconButton(onClick = {
                    onSuccessfulIntent(SuccessfulIntent.CloseClick)
                }
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 16.dp),
                        imageVector = Icons.Rounded.Close,
                        contentDescription = stringResource(id = R.string.close_button_content_description)
                    )
                }
            }
        }
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                24.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .padding(horizontal = 20.dp),
                painter = painterResource(id = R.drawable.success_img),
                contentDescription = stringResource(R.string.success_str)
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 12.dp, end = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(R.string.succes_title)
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 40.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(R.string.success2_title)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                PrimaryButton(
                    text = stringResource(R.string.success_bt),
                    enabled = true,
                    onClick = {
                        onSuccessfulIntent(SuccessfulIntent.ClickOrders)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SuccessfulAppScreenPreview() {
    VodimobileTheme(dynamicColor = false) {
        val successfulAppViewModel = SuccessfulAppViewModel()
        SuccessfulAppScreen(
            onSuccessfulIntent = successfulAppViewModel::onIntent,
            successfulEffect = successfulAppViewModel.successfulEffect,
            navHostController = rememberNavController()
        )
    }
}


@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SuccessfulAppScreenPreviewDark() {
    VodimobileTheme(dynamicColor = false) {
        val successfulAppViewModel = SuccessfulAppViewModel()
        SuccessfulAppScreen(
            onSuccessfulIntent = successfulAppViewModel::onIntent,
            successfulEffect = successfulAppViewModel.successfulEffect,
            navHostController = rememberNavController()
        )
    }
}