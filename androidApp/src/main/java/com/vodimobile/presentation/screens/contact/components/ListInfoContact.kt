package com.vodimobile.presentation.screens.contact.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme


@Composable
fun ListInfoContact(
    onPhoneClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEmailClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        InfoContactItem(
            title = stringResource(R.string.mail_str),
            subtitle = stringResource(R.string.mail1_str),
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = stringResource(R.string.mail_str),
                    modifier = Modifier
                        .size(24.dp)
                )
            },
            onClick = onEmailClick
        )
        InfoContactItem(
            title = stringResource(R.string.telephone_str),
            subtitle = stringResource(R.string.telephone1_str),
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.telephone),
                    contentDescription = stringResource(R.string.telephone_str),
                    modifier = Modifier
                        .size(24.dp)
                )
            },
            onClick = onPhoneClick
        )
        InfoContactItem(
            title = stringResource(R.string.adress_str),
            subtitle = stringResource(R.string.adress2_str),
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.telegram1),
                    contentDescription = stringResource(R.string.adress_str),
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ListInfoContactPreview() {
    VodimobileTheme {
        ListInfoContact(onPhoneClick = {}, onEmailClick = {})
    }
}
