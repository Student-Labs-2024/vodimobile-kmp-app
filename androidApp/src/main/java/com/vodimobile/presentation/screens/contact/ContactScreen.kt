package com.vodimobile.presentation.screens.contact

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vodimobile.App
import com.vodimobile.android.R
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.contact.components.ListContactItem
import com.vodimobile.presentation.screens.contact.components.ListInfoContact
import com.vodimobile.presentation.screens.contact.components.VersionItem
import com.vodimobile.presentation.screens.contact.store.ContactEffect
import com.vodimobile.presentation.screens.contact.store.ContactIntent
import com.vodimobile.presentation.screens.contact.store.ContactState
import com.vodimobile.presentation.theme.VodimobileTheme
import kotlinx.coroutines.flow.MutableSharedFlow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ComposeModifierMissing")
@Composable
fun ContactScreen(
    onContactIntent: (ContactIntent) -> Unit,
    @SuppressLint("ComposeMutableParameters") contactEffect: MutableSharedFlow<ContactEffect>,
    validYear: String,
    contactState: State<ContactState>,
    navHostController: NavHostController
) {

    LaunchedEffect(key1 = Unit) {
        contactEffect.collect { effect ->
            when (effect) {
                ContactEffect.BackClick -> {
                    navHostController.navigateUp()
                }

                ContactEffect.TelegramClick -> {
                    openTelegram()
                }

                ContactEffect.VkClick -> {
                    openVk()
                }
                ContactEffect.WhatsappClick -> {
                    openWhatsapp("+79507831440")
                }
            }
        }
    }

    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 23.dp),
                title = stringResource(R.string.title_contact_screen),
                onNavigateBack = {
                    onContactIntent(ContactIntent.BackClick)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 16.dp)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            VersionItem(version = contactState.value.version, validYear = validYear)

            Spacer(modifier = Modifier.height(20.dp))
            ListInfoContact()


            Spacer(modifier = Modifier.height(12.dp))
            ListContactItem(
                onVkClick = {
                    onContactIntent(ContactIntent.VkClick)
                },
                onWhatsappClick = {
                    onContactIntent(ContactIntent.WhatsappClick)
                },
                onTelegramClick = {
                    onContactIntent(ContactIntent.TelegramClick)
                }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ContactScreenPreviewLight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val contactViewModel = ContactViewModel()
            ContactScreen(
                onContactIntent = contactViewModel::onIntent,
                contactEffect = contactViewModel.contactEffect,
                contactState = contactViewModel.contactState.collectAsState(),
                validYear = "2010",
                navHostController = rememberNavController()
            )
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ContactScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            val contactViewModel = ContactViewModel()
            ContactScreen(
                onContactIntent = contactViewModel::onIntent,
                contactEffect = contactViewModel.contactEffect,
                contactState = contactViewModel.contactState.collectAsState(),
                validYear = "2010",
                navHostController = rememberNavController()
            )
        }
    }
}

private fun openTelegram() {
    val telegramUrl = "https://web.telegram.org/k/#@StolyarovE"

    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.INSTANCE.startActivity(intent)
    } catch (e: Exception) {

        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
        webIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.INSTANCE.startActivity(webIntent)
    }
}


private fun openWhatsapp(phoneNumber: String) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "whatsapp://send?phone=$phoneNumber"
            )
        )
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.INSTANCE.startActivity(intent)
    } catch (e: Exception) {

        val webIntent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "https://api.whatsapp.com/send?phone=$phoneNumber"
            )
        )
        webIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.INSTANCE.startActivity(webIntent)
    }
}


private fun openVk() {
    val vkUrl = "https://vk.com/vodimobil"

    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(vkUrl))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.INSTANCE.startActivity(intent)
    } catch (e: Exception) {

        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(vkUrl))
        webIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        App.INSTANCE.startActivity(webIntent)
    }
}