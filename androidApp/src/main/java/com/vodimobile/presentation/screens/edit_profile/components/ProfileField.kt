package com.vodimobile.presentation.screens.edit_profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import com.vodimobile.presentation.theme.ExtendedTheme

@Composable
fun ProfileField(
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    text: String = "",
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = text,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(color = ExtendedTheme.colorScheme.hintText)
            )
        },
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        enabled = enabled,
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
        )
    )
}
