package com.vodimobile.presentation.screens.contact.store

sealed class ContactEffect {
    data object TelegramClick : ContactEffect()
    data object VkClick : ContactEffect()
    data object WhatsappClick : ContactEffect()
    data object BackClick : ContactEffect()
    data object PhoneClick : ContactEffect()
}