package com.vodimobile.presentation.screens.contact

import androidx.lifecycle.ViewModel

class ContactViewModel : ViewModel()  {
    fun onIntent(intent: ContactIntent) {
        when(intent) {
            ContactIntent.TelegramClick -> {}
            ContactIntent.VkClick -> {}
            ContactIntent.WhatsappClick -> {}
            ContactIntent.BackClick -> {}

        }
    }
}