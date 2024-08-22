package com.vodimobile.presentation.screens.reservation.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipColors
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.vodimobile.android.R
import com.vodimobile.presentation.screens.reservation.utils.DescribableItem
import com.vodimobile.presentation.screens.reservation.utils.createFullFieldDescription
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.theme.tooltip_container
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (Pair<Int, String>) -> Unit,
    @SuppressLint("ComposeUnstableCollections") items: List<DescribableItem>,
    modifier: Modifier = Modifier,
    tooltip: String = ""
) {
    val focusManager = LocalFocusManager.current

    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Default.KeyboardArrowUp
    else
        Icons.Default.KeyboardArrowDown

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

            if (tooltip.isNotEmpty()) {

                val tooltipState = RichTooltipState()
                val scope = rememberCoroutineScope()

                RichTooltipBox(
                    modifier = Modifier.wrapContentHeight(),
                    colors = RichTooltipColors(
                        containerColor = tooltip_container,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        titleContentColor = tooltip_container,
                        actionContentColor = tooltip_container
                    ),
                    text = {
                        Text(
                            text = tooltip,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    tooltipState = tooltipState
                ) {
                    IconButton(
                        onClick = { scope.launch { tooltipState.show() } }
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = tooltip,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
    ExtendedTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                value = value,
                onValueChange = { },
                readOnly = true,
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
                        onClick = { expanded = !expanded }
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                },
                enabled = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )

            Box(modifier = Modifier.padding(8.dp)) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(color = MaterialTheme.colorScheme.onPrimary)
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                ) {
                    items.forEachIndexed { index, item ->
                        val description = createFullFieldDescription(item)
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            onClick = {
                                onValueChange(Pair(index, description))
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DescriptionFieldLightPreview() {
    VodimobileTheme(dynamicColor = false) {
        DropDownField(
            label = stringResource(id = R.string.reservation_place_get_label),
            value = "",
            placeholder = stringResource(id = R.string.reservation_place_placeholder),
            onValueChange = {},
            items = listOf()
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DescriptionFieldDarkPreview() {
    VodimobileTheme(dynamicColor = false) {
        DropDownField(
            label = stringResource(id = R.string.reservation_place_get_label),
            value = "",
            placeholder = stringResource(id = R.string.reservation_place_placeholder),
            onValueChange = {},
            items = listOf()
        )
    }
}
