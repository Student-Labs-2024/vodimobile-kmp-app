package com.vodimobile.presentation.components.block

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.user_actions.SecondaryButton
import com.vodimobile.presentation.screens.network_error.store.ConnectionErrorIntent

@Composable
fun ErrorItem(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
    onNetworkErrorIntent: (ConnectionErrorIntent) -> Unit,
    screen: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold
                )

            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = subtitle,
                style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onBackground)
            )

        }

        Spacer(modifier = Modifier.weight(1.0f))
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            SecondaryButton(
                text = stringResource(R.string.try_again_bt),
                enabled = true,
                onClick = {
                    onNetworkErrorIntent(ConnectionErrorIntent.ClickRepeat(value = screen))
                }
            )
        }
    }
}

