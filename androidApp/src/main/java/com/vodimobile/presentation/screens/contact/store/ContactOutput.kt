package com.vodimobile.presentation.screens.contact.store

sealed class ContactOutput {
    data object BackClick : ContactOutput()
    data object VkClick : ContactOutput()
    data object WhatsappClick : ContactOutput()
    data object TelegramClick : ContactOutput()
}