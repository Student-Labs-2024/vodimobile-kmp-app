package com.vodimobile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.theme.VodimobileTheme

@Composable
fun AuthenticationField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    maskVisualTransformation: VisualTransformation? = null,
    isError: Boolean,
    errorMessage: String
) {
    var isFocused by remember { mutableStateOf(false) }
    var showClearIcon by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showClearIcon = it.isNotEmpty()
            },
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                errorContainerColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorCursorColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            ),
            visualTransformation = maskVisualTransformation ?: VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small
                )
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = if (isError) MaterialTheme.colorScheme.error
                        else if (value.isNotEmpty() || isFocused) MaterialTheme.colorScheme.tertiary
                        else Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.small
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            singleLine = true,
            trailingIcon = {
                if (showClearIcon) {
                    IconButton(
                        onClick = {
                            onValueChange("")
                            showClearIcon = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.clear_registration_input),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            },
            isError = isError,
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp, top = 3.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AuthenticationFieldPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            AuthenticationField(
                label = "Email",
                value = "",
                onValueChange = {},
                placeholder = "example@gmail.com",
                keyboardType = KeyboardType.Email,
                errorMessage = "Такой почты не существует. Повторите попытку",
                isError = true
            )
        }
    }
}
