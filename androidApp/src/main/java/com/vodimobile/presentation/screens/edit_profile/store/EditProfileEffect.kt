package com.vodimobile.presentation.screens.edit_profile.store

sealed class EditProfileEffect {
    data object ClickBack : EditProfileEffect()
    data object ClickEditPassword : EditProfileEffect()
    data class SaveData(val msgResId: Int, val actionResId: Int) : EditProfileEffect()
    data object ProgressDialog : EditProfileEffect()
    data object RemoveProgressDialog : EditProfileEffect()
}
