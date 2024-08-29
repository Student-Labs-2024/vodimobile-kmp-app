package com.vodimobile.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vodimobile.android.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("ComposeModifierMissing")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerSwitchableSample(
    onTimeSelected: (String) -> Unit,
    onCancel: () -> Unit,
) {
    val state = rememberTimePickerState()
    val formatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val showingPicker = remember { mutableStateOf(true) }
    val configuration = LocalConfiguration.current
    val cal = Calendar.getInstance()
    cal.timeInMillis = System.currentTimeMillis()
    var finalTime by remember {
        mutableStateOf(formatter.format(cal.time))
    }

    val titleResource = if (showingPicker.value) R.string.select_time else R.string.input_time
    val iconResource = if (showingPicker.value) Icons.Outlined.Keyboard else Icons.Outlined.Schedule
    val contentDescriptionResource =
        if (showingPicker.value) R.string.switch_to_text_input else R.string.switch_to_touch_input
    val isExpanded = configuration.screenHeightDp > 400

    @Composable
    fun TimePickerContent() {
        if (showingPicker.value && isExpanded) {
            TimePicker(state = state)
        } else {
            TimeInput(state = state)
        }
    }

    TimePickerDialog(
        title = stringResource(id = titleResource),
        onCancel = onCancel,
        onConfirm = {
            cal.set(Calendar.HOUR_OF_DAY, state.hour)
            cal.set(Calendar.MINUTE, state.minute)

            finalTime = formatter.format(cal.timeInMillis)
            onTimeSelected(finalTime)
        },
        toggle = {
            if (isExpanded) {
                IconButton(onClick = { showingPicker.value = !showingPicker.value }) {
                    Icon(
                        iconResource,
                        contentDescription = stringResource(id = contentDescriptionResource)
                    )
                }
            }
        }
    ) {
        TimePickerContent()
    }
}

@SuppressLint("ComposeModifierMissing")
@Composable
fun TimePickerDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "Select Time",
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier =
            Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.secondaryContainer
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.displaySmall
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = onCancel) { Text(stringResource(R.string.time_cancel)) }
                    TextButton(onClick = onConfirm) { Text(stringResource(R.string.ok_time)) }
                }
            }
        }
    }
}
