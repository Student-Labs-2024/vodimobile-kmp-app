package com.vodimobile.presentation.screens.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataCard(onEditClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
        onClick = onEditClick,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 32.dp, vertical = 36.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_full),
                contentDescription = stringResource(id = R.string.profile_title),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1.0f)
            ) {
                Text(
                    text = stringResource(id = R.string.personal_data_title),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .wrapContentHeight()
                )
                Text(
                    text = stringResource(id = R.string.personal_data_subtitle),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.typography.labelSmall.color.copy(
                            alpha = 0.612f
                        )
                    ),
                    softWrap = true
                )
            }

            IconButton(
                onClick = onEditClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_grey),
                    contentDescription = stringResource(id = R.string.edit_personal_data_content_desc)
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun PersonalDataCardPreview() {
    VodimobileTheme(dynamicColor = false) {
        PersonalDataCard({})
    }
}