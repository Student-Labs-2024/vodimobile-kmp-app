package com.vodimobile.presentation.screens.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.screens.home.components.SnapVodimobileTopAppBar
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun HomeScreen() {
    Scaffold(topBar = {
        SnapVodimobileTopAppBar(
            date = System.currentTimeMillis(),
            onNotificationButtonClick = { /*TODO*/ },
            onFieldClick = { /*TODO*/ },
            onButtonClick = {}
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(paddingValues)
                .testTag(TestTags.HomeScreen.contentScroll)
        ) {

        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        HomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeScreenLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        HomeScreen()
    }
}