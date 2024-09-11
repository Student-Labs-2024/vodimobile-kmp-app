package com.vodimobile.presentation.screens.contact.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme


@Composable
fun ListContactItem(
    modifier: Modifier = Modifier,
    onVkClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    onTelegramClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(4.dp),

    ) {
        Text(
            text = stringResource(R.string.title_list_contact),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 12.dp),
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 16.sp)
        )

        ContactItem(
            title = stringResource(R.string.vk_str),
            onClick = onVkClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.vk),
                contentDescription = stringResource(id = R.string.vk_str),
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
            )
        }
        ContactItem(
            title = stringResource(R.string.whatsapp_str),
            onClick = onWhatsappClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.whatsapp),
                contentDescription = stringResource(R.string.whatsapp_str),
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
            )
        }
        ContactItem(
            title = stringResource(R.string.telegram_str),
            onClick = onTelegramClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.telegram),
                contentDescription = stringResource(R.string.telegram_str),
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
            )
        }
    }
}


@Preview(showBackground = true,  uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ListContactItemPreviewLight() {
    VodimobileTheme {
        ListContactItem(modifier = Modifier, {}, {}, {})
    }
}

@Preview(showBackground = true,  uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListContactItemPreviewNight() {
    VodimobileTheme {
        ListContactItem(modifier = Modifier, {}, {}, {})
    }
}

