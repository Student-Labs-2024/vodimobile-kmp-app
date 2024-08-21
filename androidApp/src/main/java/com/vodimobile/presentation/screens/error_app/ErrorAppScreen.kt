package com.vodimobile.presentation.screens.error_app

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.android.R
import com.vodimobile.presentation.DialogIdentifiers
import com.vodimobile.presentation.components.SecondaryButton
import com.vodimobile.presentation.screens.error_app.store.ErrorAppEffect
import com.vodimobile.presentation.screens.error_app.store.ErrorAppIntent
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow


@SuppressLint("ComposeModifierMissing")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorAppScreen(
    onErrorAppIntent: (ErrorAppIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") errorAppEffect: MutableSharedFlow<ErrorAppEffect>,
    navHostController: NavHostController
) {
    LaunchedEffect(key1 = Unit) {
        errorAppEffect.collect { effect ->
            when (effect) {
                ErrorAppEffect.BackClick -> {
                    navHostController.navigateUp()
                }

                ErrorAppEffect.RepeatClick -> {
                    navHostController.navigateUp()
                }

                ErrorAppEffect.ProgressDialog -> {
                    navHostController.navigate(route = DialogIdentifiers.LOADING_DIALOG)
                }

                ErrorAppEffect.RemoveProgressDialog -> {
                    navHostController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(12.dp),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        onErrorAppIntent(ErrorAppIntent.BackClick)
                    }) {
                        Icon(
                            modifier = Modifier.padding(start = 16.dp),
                            tint = MaterialTheme.colorScheme.onBackground,
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.error_icon)
                        )
                    }
                }
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
                painter = painterResource(id = R.drawable.error_app),
                contentDescription = stringResource(R.string.error_app)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.error_app_title)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    text = stringResource(R.string.error_app_title2)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                SecondaryButton(
                    text = stringResource(R.string.error_bt),
                    enabled = true,
                    onClick = {
                        onErrorAppIntent(ErrorAppIntent.RepeatClick)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ErrorAppScreenPreview() {
    VodimobileTheme(dynamicColor = false) {
        val errorAppViewModel = ErrorAppViewModel()
        ErrorAppScreen(
            onErrorAppIntent = errorAppViewModel::onIntent,
            errorAppEffect = errorAppViewModel.errorAppEffect,
            navHostController = rememberNavController()
        )
    }
}