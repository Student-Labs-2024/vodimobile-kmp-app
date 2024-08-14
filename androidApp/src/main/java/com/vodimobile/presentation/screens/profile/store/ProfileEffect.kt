package com.vodimobile.presentation.screens.profile.store

sealed class ProfileEffect {
    data object PersonalDataClick : ProfileEffect()
    data object RulesClick : ProfileEffect()
    data object FaqClick : ProfileEffect()
    data object ContactsClick : ProfileEffect()
    data object AppExitClick : ProfileEffect()
}