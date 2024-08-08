package com.vodimobile.presentation.screens.reservation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun DescriptionTimeField(
    label: String,
    value: String,
    placeholder: String,
    onClick: () -> Unit,
    onValueChange: () -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        ExtendedTheme {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = value,
                onValueChange = { onValueChange() },
                placeholder = {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    focusedContainerColor = ExtendedTheme.colorScheme.containerBack,
                    unfocusedContainerColor = ExtendedTheme.colorScheme.containerBack,
                    disabledContainerColor = ExtendedTheme.colorScheme.containerBack,
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    errorContainerColor = ExtendedTheme.colorScheme.containerBack,
                    errorCursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    errorTextColor = MaterialTheme.colorScheme.onBackground
                ),
                shape = MaterialTheme.shapes.small,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = onClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                enabled = true,
                textStyle = MaterialTheme.typography.bodySmall,
                isError = isError
            )
        }
        if (isError) {
            Text(
                text = stringResource(id = R.string.error_time),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp, top = 3.dp)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DescriptionTimeFieldLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        DescriptionTimeField(
            label = stringResource(id = R.string.reservation_place_label),
            value = "",
            placeholder = stringResource(id = R.string.reservation_place_placeholder),
            onClick = {},
            onValueChange = {},
            isError = false
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DescriptionTimeFieldDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        DescriptionTimeField(
            label = stringResource(id = R.string.reservation_place_label),
            value = "",
            placeholder = stringResource(id = R.string.reservation_place_placeholder),
            onClick = {},
            onValueChange = {},
            isError = false
        )
    }
}
