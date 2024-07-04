package com.vodimobile.presentation.screens.registration.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
                        color = if (checkBoxState.value) Color(0xFF1958EE)
                        else Color(0xFF939BAA)
                    ),
                    shape = RoundedCornerShape(6.dp)
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
                    tint = Color(0xFF1958EE),
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
                color = Color(0xFF939BAA),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = agreementTextPart2,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF1958EE),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.clickable(onClick = onClickUserAgreement)
            )
        }
    }
}


@Preview
@Composable
fun AgreementCheckBoxFalsePreview() {

    AgreementCheckBox(
        isChecked = false,
        onCheckedChange = {},
        onClickUserAgreement = {})
}

@Preview
@Composable
fun AgreementCheckBoxTruePreview() {

    AgreementCheckBox(
        isChecked = true,
        onCheckedChange = {},
        onClickUserAgreement = {})
}
