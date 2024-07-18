package com.vodimobile.presentation.screens.contact

import androidx.lifecycle.ViewModel
import com.vodimobile.presentation.screens.contact.store.ContactIntent
import com.vodimobile.presentation.screens.contact.store.ContactOutput

class ContactViewModel(
    private val output: (ContactOutput) -> Unit
) : ViewModel() {
    fun onIntent(intent: ContactIntent) {
        when (intent) {
            ContactIntent.TelegramClick -> {
                onOutput(ContactOutput.TelegramClick)
            }
            ContactIntent.VkClick -> {
                onOutput(ContactOutput.VkClick)
            }
            ContactIntent.WhatsappClick -> {
                onOutput(ContactOutput.WhatsappClick)
            }
            ContactIntent.BackClick -> {
                onOutput(ContactOutput.BackClick)
            }

        }
    }

    private fun onOutput(o: ContactOutput) {
        output(o)
    }
}