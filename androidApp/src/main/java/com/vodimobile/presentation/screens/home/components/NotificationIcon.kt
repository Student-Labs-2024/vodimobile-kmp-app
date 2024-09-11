package com.vodimobile.presentation.screens.home.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun NotificationIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = onClick) {
            Icon(
                Icons.Outlined.Notifications,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = stringResource(id = R.string.notification_desc)
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun NotificationItemLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold(containerColor = MaterialTheme.colorScheme.primary) {
            NotificationIcon(onClick = {})
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NotificationItemNightPreview() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold(containerColor = MaterialTheme.colorScheme.primary) {
            NotificationIcon(onClick = {})
        }
    }
}
