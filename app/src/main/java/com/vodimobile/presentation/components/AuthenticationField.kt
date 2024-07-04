package com.vodimobile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthenticationField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    clearIconContentDescription: String,
    maskVisualTransformation: VisualTransformation? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    var showClearIcon by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF6F6F6),
                    shape = MaterialTheme.shapes.extraSmall
                )
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = if (isFocused) Color(0XFF9CA3B0) else Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.extraSmall
                )
        ) {
            TextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    showClearIcon = it.isNotEmpty()
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color(0xFF939BAA),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = keyboardType
                ),
                visualTransformation = maskVisualTransformation ?: VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                singleLine = true
            )

            if (showClearIcon) {
                IconButton(
                    onClick = {
                        onValueChange("")
                        showClearIcon = false
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = clearIconContentDescription,
                        tint = Color(0xFF9CA3B0)
                    )
                }
            }
        }
    }
}
