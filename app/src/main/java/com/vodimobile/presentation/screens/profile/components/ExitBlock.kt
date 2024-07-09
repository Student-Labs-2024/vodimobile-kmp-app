package com.vodimobile.presentation.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vodimobile.R

@Composable
fun ExitBlock(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val itemModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .then(modifier)

    Card(
        onClick = onClick,
        modifier = itemModifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp, alignment = Alignment.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(PaddingValues(horizontal = 28.dp, vertical = 16.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.exit_account),
                contentDescription = stringResource(id = R.string.exit_account),
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = stringResource(id = R.string.exit_account),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .weight(1.0f)
            )
        }
    }
}