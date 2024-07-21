package com.vodimobile.presentation.screens.faq.store

sealed class FaqEffect {
    data object BackClick : FaqEffect()
}