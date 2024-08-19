package com.vodimobile.presentation.screens.profile.store

sealed class ProfileIntent {
    data object PersonalDataClick : ProfileIntent()
    data object RulesClick : ProfileIntent()
    data object FaqClick : ProfileIntent()
    data object ContactsClick : ProfileIntent()
    data object AppExitClick : ProfileIntent()
}