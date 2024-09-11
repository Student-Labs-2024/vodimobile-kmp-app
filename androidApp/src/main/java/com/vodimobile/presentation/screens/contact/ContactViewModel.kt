package com.vodimobile.presentation.screens.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.android.BuildConfig
import com.vodimobile.presentation.screens.contact.store.ContactEffect
import com.vodimobile.presentation.screens.contact.store.ContactIntent
import com.vodimobile.presentation.screens.contact.store.ContactState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ContactViewModel : ViewModel() {

    val contactEffect = MutableSharedFlow<ContactEffect>()
    val contactState = MutableStateFlow(
        ContactState(
            version = getVersion(),

            )
    )

    fun onIntent(intent: ContactIntent) {
        when (intent) {
            ContactIntent.TelegramClick -> {
                viewModelScope.launch {
                    contactEffect.emit(ContactEffect.TelegramClick)
                }
            }

            ContactIntent.VkClick -> {
                viewModelScope.launch {
                    contactEffect.emit(ContactEffect.VkClick)
                }
            }

            ContactIntent.WhatsappClick -> {
                viewModelScope.launch {
                    contactEffect.emit(ContactEffect.WhatsappClick)
                }
            }

            ContactIntent.BackClick -> {
                viewModelScope.launch {
                    contactEffect.emit(ContactEffect.BackClick)
                }
            }

            is ContactIntent.PhoneClick -> {
                viewModelScope.launch {
                    contactEffect.emit(ContactEffect.PhoneClick)
                }
            }
            is ContactIntent.EmailClick -> {
                viewModelScope.launch {
                    contactEffect.emit(ContactEffect.EmailClick)
                }
            }
        }
    }

    private fun getVersion(): String {
        val versionName = BuildConfig.VERSION_NAME
        return versionName
    }

    fun getValidYear(startYear: String): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = System.currentTimeMillis()
        val currentYear = cal.get(Calendar.YEAR)
        val versionYear = "$startYear-$currentYear"
        return versionYear
    }
}
