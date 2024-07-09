package com.vodimobile.presentation.screens.profile.store

sealed class ProfileIntent {
    data object personalDataClick : ProfileIntent()
    data object rulesClick : ProfileIntent()
    data object faqClick : ProfileIntent()
    data object constantsClick : ProfileIntent()
    data object appExitClick : ProfileIntent()
}