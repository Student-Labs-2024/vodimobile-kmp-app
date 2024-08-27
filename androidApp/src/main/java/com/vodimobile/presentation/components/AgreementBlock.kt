package com.vodimobile.presentation.components

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R

@SuppressLint("ComposeModifierMissing")
@Composable
fun AgreementBlock(
    onClickUserAgreement: () -> Unit,
    onClickNextButton: () -> Unit,
    enabled: Boolean,
) {

    var isAgreementChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PrimaryButton(
            text = stringResource(id = R.string.text_next_button),
            enabled = enabled && isAgreementChecked,
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


@Preview
@Composable
private fun AgreementBlocddk() {
    AgreementBlock(
        onClickUserAgreement = { /*TODO*/ },
        onClickNextButton = { /*TODO*/ },
        enabled = false
    )
}