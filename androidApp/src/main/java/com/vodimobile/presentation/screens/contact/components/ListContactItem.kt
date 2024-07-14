package com.vodimobile.presentation.screens.contact.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Text(
                text = stringResource(R.string.title_list_contact),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 12.dp),
                fontSize = 16.sp,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        ContactItem(
            title = stringResource(R.string.vk_str),
            onClick = onVkClick
        ) {
            Image(
                painter = painterResource(id = R.drawable.vk),
                contentDescription = null,
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
                contentDescription = null,
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
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListContactItemPreview() {
    VodimobileTheme {
        ListContactItem(modifier = Modifier, {}, {}, {})
    }
}
