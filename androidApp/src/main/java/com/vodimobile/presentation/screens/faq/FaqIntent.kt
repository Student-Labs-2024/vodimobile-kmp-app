package com.vodimobile.presentation.screens.faq

sealed class FaqIntent {
    data object BackClick : FaqIntent()
}