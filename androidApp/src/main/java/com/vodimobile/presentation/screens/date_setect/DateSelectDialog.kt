package com.vodimobile.presentation.screens.date_setect

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vodimobile.android.R
import com.vodimobile.presentation.DateFormat
import com.vodimobile.presentation.theme.ExtendedTheme
import com.vodimobile.presentation.theme.VodimobileTheme
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalComposeUiApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ComposeModifierMissing")
@Composable
fun DateSelectDialog(
    onDismissClick: () -> Unit,
    onConfirmClick: (Long, Long) -> Unit,
    initialDateInMillis: LongArray
) {
    val cal = Calendar.getInstance()
    cal.timeInMillis = System.currentTimeMillis()
    val currentYear = cal.get(Calendar.YEAR)

    ExtendedTheme {
        val datePickerState =
            rememberDateRangePickerState(
                initialSelectedStartDateMillis = initialDateInMillis[0],
                initialSelectedEndDateMillis = initialDateInMillis[1],
                yearRange = (currentYear - 1)..currentYear
            )
        val selectedStartDateMillis = datePickerState.selectedStartDateMillis?.let {
            convertMillisToDate(it)
        }
        val selectedEndDateMillis = datePickerState.selectedEndDateMillis?.let {
            convertMillisToDate(it)
        }


        val colors = DatePickerDefaults.colors(
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            headlineContentColor = MaterialTheme.colorScheme.onBackground,
            weekdayContentColor = ExtendedTheme.colorScheme.hintText,
            subheadContentColor = MaterialTheme.colorScheme.onBackground,
            yearContentColor = MaterialTheme.colorScheme.onBackground,

            selectedDayContainerColor = MaterialTheme.colorScheme.primary,
            selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,

            currentYearContentColor = MaterialTheme.colorScheme.primaryContainer,

            todayDateBorderColor = MaterialTheme.colorScheme.primaryContainer,
            todayContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        DatePickerDialog(
            modifier = Modifier.wrapContentSize(),
            colors = colors,
            onDismissRequest = onDismissClick,
            confirmButton = {},
            dismissButton = {},
            tonalElevation = 0.dp,
            shape = RoundedCornerShape(13.dp),
        ) {
            DateRangePicker(
                state = datePickerState,
                title = {},
                headline = {
                    Text(
                        text = "$selectedStartDateMillis - $selectedEndDateMillis",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                },
                showModeToggle = true,
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Divider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = onDismissClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = ExtendedTheme.colorScheme.hintText
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.cansel),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                TextButton(
                    onClick = {
                        onConfirmClick(
                            datePickerState.selectedStartDateMillis ?: initialDateInMillis[0],
                            datePickerState.selectedEndDateMillis ?: initialDateInMillis[1]
                        )
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
    }
}

private fun convertMillisToDate(millis: Long): String {
    return DateFormat.formatter.format(Date(millis))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "RU"
)
private fun DateSelectDialogPreviewDarkRu() {
    VodimobileTheme(dynamicColor = false) {
        DateSelectDialog(
            {},
            { l, o -> },
            longArrayOf(System.currentTimeMillis(), System.currentTimeMillis())
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "RU"
)
private fun DateSelectDialogPreviewLightRu() {
    VodimobileTheme(dynamicColor = false) {
        DateSelectDialog(
            {},
            { l, o -> },
            longArrayOf(System.currentTimeMillis(), System.currentTimeMillis())
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "EN"
)
private fun DateSelectDialogPreviewDarkEn() {
    VodimobileTheme(dynamicColor = false) {
        DateSelectDialog(
            {},
            { l, o -> },
            longArrayOf(System.currentTimeMillis(), System.currentTimeMillis())
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "EN"
)
private fun DateSelectDialogPreviewLightEN() {
    VodimobileTheme(dynamicColor = false) {
        DateSelectDialog(
            {},
            { l, o -> },
            longArrayOf(System.currentTimeMillis(), System.currentTimeMillis())
        )
    }
}