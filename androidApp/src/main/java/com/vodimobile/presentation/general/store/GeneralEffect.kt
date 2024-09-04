package com.vodimobile.presentation.general.store

sealed class GeneralEffect {
    data object UnauthedUser : GeneralEffect()
}