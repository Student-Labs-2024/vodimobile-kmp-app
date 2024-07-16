package com.vodimobile.presentation.screens.contact.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vodimobile.presentation.BottomAppBarAlpha


@Composable
fun ContactItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,



    ) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        contentPadding = PaddingValues(start = 24.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )

        ) {

            icon()

            Text(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(vertical = 9.5.dp),
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = LocalContentColor.current.copy(alpha = BottomAppBarAlpha.BACKGROUND_ALPHA)
            )
        }
    }
}