package com.vodimobile.presentation.screens.rules.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.BottomAppBarAlpha
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun RulesItem(
    modifier: Modifier = Modifier,
    title: String,
    onNavigate: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onNavigate,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = MaterialTheme.colorScheme.onBackground),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            Text(
                modifier = Modifier.weight(1.0f),
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )

            Icon(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = stringResource(R.string.str_arrowforward),
                modifier = Modifier.size(24.dp),
                tint = LocalContentColor.current.copy(alpha = BottomAppBarAlpha.BACKGROUND_ALPHA)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RulesItemPreview() {
    VodimobileTheme {
        RulesItem(title = "Условия аренды для водителей") {}
    }
}