package com.vodimobile.presentation.screens.edit_profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun ProfileNameField(
    onValueChange: (String) -> Unit,
    isError: Boolean,
    value: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val maxNameLength = 100
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.full_name),
                    style = MaterialTheme.typography.labelMedium.copy(color = ExtendedTheme.colorScheme.hintText)
                )
            },
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,

                errorTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,

                focusedLabelColor = ExtendedTheme.colorScheme.hintText,
                unfocusedLabelColor = ExtendedTheme.colorScheme.hintText,
                disabledLabelColor = ExtendedTheme.colorScheme.hintText,
                errorLabelColor = ExtendedTheme.colorScheme.hintText,

                focusedIndicatorColor = ExtendedTheme.colorScheme.hintText,
                unfocusedIndicatorColor = ExtendedTheme.colorScheme.hintText,
                errorIndicatorColor = ExtendedTheme.colorScheme.hintText,
                disabledIndicatorColor = ExtendedTheme.colorScheme.hintText
            ),
            isError = isError
        )
        if (isError) {
            Text(
                text = when {
                    value.length > maxNameLength -> stringResource(id = R.string.name_too_long)
                    else -> stringResource(id = R.string.name_error)
                },
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 12.dp, top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileNameFieldPreview() {
    VodimobileTheme(dynamicColor = false) {
        ProfileNameField(
            onValueChange = {},
            isError = true,
            value = "",
        )
    }
}
