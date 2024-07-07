package com.vodimobile.presentation.screens.registration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vodimobile.R
import com.vodimobile.presentation.components.PrimaryButton

@Composable
fun AgreementBlock(
    onClickUserAgreement: () -> Unit,
    onClickNextButton: () -> Unit,
) {

    var isAgreementChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.text_next_button),
            enabled = isAgreementChecked,
            onClick = onClickNextButton
        )
        Spacer(modifier = Modifier.height(12.dp))
        AgreementCheckBox(
            isChecked = isAgreementChecked,
            onCheckedChange = {
                isAgreementChecked = it
            },
            onClickUserAgreement = onClickUserAgreement
        )
    }
}