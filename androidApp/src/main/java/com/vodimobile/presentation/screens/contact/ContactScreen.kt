package com.vodimobile.presentation.screens.contact

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
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
import com.vodimobile.presentation.theme.VodimobileTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ContactScreen(ContactViewModel: ContactViewModel) {
    Scaffold(
        topBar = {
            ScreenHeader(
                modifier = Modifier.padding(top = 23.dp),
                title = stringResource(R.string.title_contact_screen),
                onNavigateBack = {
                    ContactViewModel.onIntent(ContactIntent.BackClick)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(top = 16.dp)
                .background(color = MaterialTheme.colorScheme.onPrimary)
        ) {
            VersionItem()

            Spacer(modifier = Modifier.height(20.dp))
            ListInfoContact()


            Spacer(modifier = Modifier.height(12.dp))
            ListContactItem(
                onVkClick = {
                    ContactViewModel.onIntent(ContactIntent.VkClick)
                },
                onWhatsappClick = {
                    ContactViewModel.onIntent(ContactIntent.WhatsappClick)
                },
                onTelegramClick = {
                    ContactViewModel.onIntent(ContactIntent.TelegramClick)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ContactScreenPreview() {
    VodimobileTheme {
        ContactScreen(ContactViewModel = ContactViewModel())
    }

}



