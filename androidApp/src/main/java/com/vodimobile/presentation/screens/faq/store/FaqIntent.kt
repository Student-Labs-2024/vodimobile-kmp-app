package com.vodimobile.presentation.screens.faq.store

sealed class FaqIntent {
    data object BackClick : FaqIntent()
}