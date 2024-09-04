package com.vodimobile.presentation.store

sealed class GeneralEffect {
    data object UnauthedUser : GeneralEffect()
}