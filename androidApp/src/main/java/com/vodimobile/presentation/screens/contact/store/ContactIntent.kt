package com.vodimobile.presentation.screens.contact.store

sealed class ContactIntent {
    data object BackClick : ContactIntent()
    data object VkClick : ContactIntent()
    data object WhatsappClick : ContactIntent()
    data object TelegramClick : ContactIntent()
    data object PhoneClick: ContactIntent()
}