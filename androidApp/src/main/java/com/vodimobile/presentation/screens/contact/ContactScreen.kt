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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vodimobile.android.R
import com.vodimobile.presentation.components.ScreenHeader
import com.vodimobile.presentation.screens.contact.components.ListContactItem
import com.vodimobile.presentation.screens.contact.components.ListInfoContact
import com.vodimobile.presentation.screens.contact.components.VersionItem
import com.vodimobile.presentation.screens.contact.store.ContactIntent
import com.vodimobile.presentation.theme.VodimobileTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactScreen(сontactViewModel: ContactViewModel) {
    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 23.dp),
                title = stringResource(R.string.title_contact_screen),
                onNavigateBack = {
                    сontactViewModel.onIntent(ContactIntent.BackClick)
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
            VersionItem(contactViewModel = ContactViewModel())

            Spacer(modifier = Modifier.height(20.dp))
            ListInfoContact()


            Spacer(modifier = Modifier.height(12.dp))
            ListContactItem(
                onVkClick = {
                    сontactViewModel.onIntent(ContactIntent.VkClick)
                },
                onWhatsappClick = {
                    сontactViewModel.onIntent(ContactIntent.WhatsappClick)
                },
                onTelegramClick = {
                    сontactViewModel.onIntent(ContactIntent.TelegramClick)
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
            ContactScreen(сontactViewModel = ContactViewModel())
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ContactScreenPreviewNight() {
    VodimobileTheme(dynamicColor = false) {
        Scaffold {
            ContactScreen(сontactViewModel = ContactViewModel())
        }
    }
}



