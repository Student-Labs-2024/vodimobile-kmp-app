package com.vodimobile.presentation.screens.profile.store

import com.vodimobile.domain.model.User

data class ProfileState(
    val user: User = User.empty()
)
