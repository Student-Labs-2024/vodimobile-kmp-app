package com.vodimobile.presentation.screens.edit_profile.store

data class EditProfileState(
    val fullName: String = "",
    val phone: String = "",
    val isFullNameError: Boolean = false
)