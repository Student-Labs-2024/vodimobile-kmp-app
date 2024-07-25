package com.vodimobile.presentation.screens.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.TestTags
import com.vodimobile.presentation.screens.edit_profile.components.FlexibleTopBar
import com.vodimobile.presentation.screens.edit_profile.components.FlexibleTopBarDefaults
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.DatePatterns

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnapVodimobileTopAppBar(
    date: Long,
    onNotificationButtonClick: () -> Unit,
    onFieldClick: () -> Unit,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    val smallScrollBarBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val largeScrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            title = {},
            actions = {
                NotificationIcon(onClick = onNotificationButtonClick)
            },
            scrollBehavior = smallScrollBarBehavior,
        )

        FlexibleTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            shape = FlexibleTopBarDefaults.topAppBarShape(size = MaterialTheme.shapes.extraLarge.topStart),
            colors = FlexibleTopBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            content = {
                DateRentBlock(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
                        .wrapContentHeight(),
                    onFieldClick = onFieldClick,
                    onButtonClick = onButtonClick,
                    date = DatePatterns.fullDate(date = date),
                    placeholder = stringResource(id = R.string.date_rent_placeholder)
                )
            },
            scrollBehavior = largeScrollBehavior,
        )
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SnapVodimobileTopAppBarDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        SnapVodimobileTopAppBar(
            onNotificationButtonClick = {},
            onFieldClick = {},
            onButtonClick = {},
            date = System.currentTimeMillis()
        )
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SnapVodimobileTopAppBarLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        SnapVodimobileTopAppBar(
            onNotificationButtonClick = {},
            onFieldClick = {},
            onButtonClick = {},
            date = System.currentTimeMillis()
        )
    }
}