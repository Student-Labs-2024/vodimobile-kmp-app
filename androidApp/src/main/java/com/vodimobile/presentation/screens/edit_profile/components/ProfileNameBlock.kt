package com.vodimobile.presentation.screens.edit_profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vodimobile.presentation.screens.edit_profile.store.EditProfileState
import com.vodimobile.presentation.theme.VodimobileTheme

@SuppressLint("ComposeModifierMissing")
@Composable
fun ProfileNameBlock(
    editProfileState: EditProfileState,
    onNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
    ) {
        ProfileNameField(
            onValueChange = onNameChanged,
            isError = editProfileState.isFullNameError,
            value = editProfileState.fullName
        )
    }
}


@Preview
@Composable
private fun RProfileBlockPreview() {
    VodimobileTheme(dynamicColor = false) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            ProfileNameBlock(
                editProfileState = EditProfileState(),
                onNameChanged = {},
            )
        }
    }
}
