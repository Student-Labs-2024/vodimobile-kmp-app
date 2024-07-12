package com.vodimobile.presentation.screens.profile.store

sealed class ProfileIntent {
    data object PersonalDataClick : ProfileIntent()
    data object RulesClick : ProfileIntent()
    data object FaqClick : ProfileIntent()
    data object ConstantsClick : ProfileIntent()
    data object AppExitClick : ProfileIntent()
}