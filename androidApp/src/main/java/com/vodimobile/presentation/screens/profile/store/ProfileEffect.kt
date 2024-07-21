package com.vodimobile.presentation.screens.profile.store

sealed class ProfileEffect {
    data object PersonalDataClick : ProfileEffect()
    data object RulesClick : ProfileEffect()
    data object FaqClick : ProfileEffect()
    data object ConstantsClick : ProfileEffect()
    data object AppExitClick : ProfileEffect()
}