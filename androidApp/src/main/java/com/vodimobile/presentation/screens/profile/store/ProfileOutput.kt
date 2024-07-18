package com.vodimobile.presentation.screens.profile.store

sealed class ProfileOutput {
    data object PersonalDataClick : ProfileOutput()
    data object RulesClick : ProfileOutput()
    data object FaqClick : ProfileOutput()
    data object ConstantsClick : ProfileOutput()
    data object AppExitClick : ProfileOutput()
}