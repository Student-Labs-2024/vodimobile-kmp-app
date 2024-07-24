package com.vodimobile.presentation.screens.edit_profile.store

sealed class EditProfileIntent {
    data class EditFullName(val fullName: String) : EditProfileIntent()
    data object SaveData : EditProfileIntent()
    data object EditPasswordClick : EditProfileIntent()
    data object ClickBack : EditProfileIntent()
}