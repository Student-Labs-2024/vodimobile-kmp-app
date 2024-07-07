package com.vodimobile.presentation.screens.registration.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.R
import com.vodimobile.presentation.theme.VodimobileTheme
import com.vodimobile.presentation.utils.splitAgreementText


@Composable
fun AgreementCheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClickUserAgreement: () -> Unit,
) {
    val checkBoxState = remember { mutableStateOf(isChecked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                horizontal = 12.dp
            )
    ) {
        Box(
            modifier = Modifier
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = if (checkBoxState.value) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    shape = MaterialTheme.shapes.extraSmall
                )
                .size(24.dp)
                .background(Color.Transparent)
                .clickable {
                    onCheckedChange(!checkBoxState.value)
                    checkBoxState.value = !checkBoxState.value
                }
        ) {
            if (checkBoxState.value)
                Icon(
                    Icons.Default.Check,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(id = R.string.agreement_checkBox_desc)
                )
        }

        Spacer(modifier = Modifier.width(16.dp))

        val (agreementTextPart1, agreementTextPart2) =
            splitAgreementText(
                agreementText = stringResource(id = R.string.agreement_text),
                delimiterChar = '\n'
            )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = agreementTextPart1,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            TextButton(
                onClick = onClickUserAgreement,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(18.dp)
            ) {
                Text(
                    text = agreementTextPart2,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
fun AgreementCheckBoxFalsePreview() {

    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            AgreementCheckBox(
                isChecked = false,
                onCheckedChange = {},
                onClickUserAgreement = {})
        }
    }
}

@Preview
@Composable
fun AgreementCheckBoxTruePreview() {

    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            AgreementCheckBox(
                isChecked = true,
                onCheckedChange = {},
                onClickUserAgreement = {})
        }
    }
}
