package com.vodimobile.presentation.screens.successful_app

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.TopAppBar
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vodimobile.presentation.LeafOrdersScreen
import com.vodimobile.presentation.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint( "ComposeModifierMissing")
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
                    navHostController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 12.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                navigationIcon = {
                    IconButton(onClick = {
                        onSuccessfulIntent(SuccessfulIntent.CloseClick)
                    }) {
                        Icon(
                            modifier = Modifier.padding(start = 16.dp),
                            tint = MaterialTheme.colorScheme.onBackground,
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(id = R.string.close_button_content_description)
                        )

                    }
                },
                title = {}
            )
        }
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .padding(horizontal = 20.dp),
                painter = painterResource(id = R.drawable.success_img),
                contentDescription = stringResource(R.string.success_str)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.succes_title)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(R.string.success2_title)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

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


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
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


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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