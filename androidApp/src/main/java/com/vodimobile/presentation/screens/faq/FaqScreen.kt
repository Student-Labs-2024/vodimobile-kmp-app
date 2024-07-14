package com.vodimobile.presentation.screens.faq

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.faq.components.FaqHeader
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun FaqScreen() {
    Scaffold(topBar = {
        ScreenHeader(title = "FAQ", onNavigateBack = {})
    }) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            item {
                FaqHeader()
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FaqScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        FaqScreen()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FaqScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        FaqScreen()
    }
}