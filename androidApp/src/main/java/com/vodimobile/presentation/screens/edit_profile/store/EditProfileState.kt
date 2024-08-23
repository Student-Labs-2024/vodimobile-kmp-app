package com.vodimobile.presentation.screens.edit_profile.store

import com.vodimobile.domain.model.User

data class EditProfileState(
    val fullName: String = "",
    val phone: String = "",
    val user: User = User.empty(),
    val isFullNameError: Boolean = false
)
