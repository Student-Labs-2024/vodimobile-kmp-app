package com.vodimobile.presentation.screens.contact

import android.annotation.SuppressLint
import android.content.res.Configuration
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactScreen(
    onContactIntent: (ContactIntent) -> Unit,
    contactEffect: MutableSharedFlow<ContactEffect>,
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
                ContactEffect.TelegramClick -> {}
                ContactEffect.VkClick -> {}
                ContactEffect.WhatsappClick -> {}
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



