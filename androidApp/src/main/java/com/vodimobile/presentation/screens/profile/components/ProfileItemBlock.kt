package com.vodimobile.presentation.screens.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.theme.normal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItemBlock(
    title: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(width = 184.dp, height = 180.dp)
            .then(modifier),
        shape = normal,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                icon()
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ProfileItemBlockLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        ProfileItemBlock(
            title = stringResource(id = R.string.personal_data_title),
            onClick = {},
            icon = {
                Image(
                    modifier = Modifier.size(width = 100.dp, height = 80.dp),
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = stringResource(id = R.string.personal_data_title)
                )
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfileItemBlockDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        ProfileItemBlock(
            title = stringResource(id = R.string.personal_data_title),
            onClick = {},
            icon = {
                Image(
                    modifier = Modifier.size(width = 100.dp, height = 80.dp),
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = stringResource(id = R.string.personal_data_title)
                )
            }
        )
    }
}
