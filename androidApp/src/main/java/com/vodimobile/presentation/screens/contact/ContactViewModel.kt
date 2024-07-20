package com.vodimobile.presentation.screens.contact

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.vodimobile.android.BuildConfig
import com.vodimobile.android.R
import com.vodimobile.presentation.screens.contact.store.ContactIntent
import java.util.Calendar

class ContactViewModel : ViewModel() {
    fun getVersionName(): String = BuildConfig.VERSION_NAME

    fun getVersionYear(context: Context): String {
        val startYearStringId = R.string.version_year_str
        val startYear = context.getString(startYearStringId)
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return "$startYear-$currentYear"
    }


    fun onIntent(intent: ContactIntent) {
        when (intent) {
            ContactIntent.TelegramClick -> {

            }

            ContactIntent.VkClick -> {

            }

            ContactIntent.WhatsappClick -> {

            }

            ContactIntent.BackClick -> {

            }
        }
    }
}
