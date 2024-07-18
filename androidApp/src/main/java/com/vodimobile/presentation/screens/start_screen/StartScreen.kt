package com.vodimobile.presentation.screens.start_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.components.SecondaryButton
import com.vodimobile.presentation.screens.start_screen.store.StartScreenIntent
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun StartScreen(startScreenViewModel: StartScreenViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 20.dp, alignment = Alignment.Top)
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(
                onClick = {
                    startScreenViewModel.onIntent(StartScreenIntent.CloseClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = stringResource(id = R.string.close_button_content_description)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(20.dp),
        )
        Image(
            painter = painterResource(id = R.drawable.logoapp),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 58.dp)
                .size(width = 250.dp, height = 133.33.dp)
        )
        PrimaryButton(
            text = stringResource(id = R.string.requister_str),
            enabled = true,
            onClick = {
                startScreenViewModel.onIntent(StartScreenIntent.ClickRegistration)
            })
        SecondaryButton(
            text = stringResource(id = R.string.login_str),
            enabled = true,
            onClick = {
                startScreenViewModel.onIntent(StartScreenIntent.ClickLogin)
            })
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun StartScreenPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            StartScreen(startScreenViewModel = StartScreenViewModel({}))
        }
    }
}