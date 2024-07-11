package com.vodimobile.presentation.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ContactsFaqRulesItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    leadingIcon: @Composable () -> Unit
) {
    val itemModifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .then(modifier)

    Button(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
        modifier = itemModifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.small,
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
        ) {

            leadingIcon()

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.612f)
            )
        }
    }
}