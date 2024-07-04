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
import androidx.compose.ui.unit.dp

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
        NextButton(
            isEnabled = isAgreementChecked,
            onNextClicked = onClickNextButton
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
