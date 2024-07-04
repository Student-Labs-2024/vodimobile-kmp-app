package com.vodimobile.presentation.startscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.R
import com.vodimobile.presentation.components.PrimaryButton
import com.vodimobile.presentation.components.SecondaryButton
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
            text = stringResource(R.string.requister_str),
            enabled = true,
            onClick = {
                startScreenViewModel.onIntent(Intent.ClickRegistration)
            })
        SecondaryButton(
            text = stringResource(R.string.login_str),
            enabled = true,
            onClick = {
                startScreenViewModel.onIntent(Intent.ClickLogin)
            })
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
private fun StartScreenPreview() {
    VodimobileTheme {
        Scaffold {
            StartScreen(startScreenViewModel = StartScreenViewModel())
        }
    }
}