package com.vodimobile.di

import com.vodimobile.presentation.screens.start_screen.StartScreenViewModel
import com.vodimobile.presentation.screens.user_agreement.UserAgreementViewModel
import com.vodimobile.presentation.screens.faq.FaqViewModel
import com.vodimobile.presentation.screens.contact.ContactViewModel
import com.vodimobile.presentation.screens.edit_profile.EditProfileViewModel
import com.vodimobile.presentation.screens.profile.ProfileViewModel
import com.vodimobile.presentation.screens.registration.RegistrationViewModel
import com.vodimobile.presentation.screens.rule_details.RuleDetailsViewModel
import com.vodimobile.presentation.screens.rules.RulesViewModel
import com.vodimobile.presentation.screens.sms.SmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
To use injected view models write for example -
val startScreenViewModel: StartScreenViewModel by viewModel() or
val startScreenViewModel: StartScreenViewModel = koinViewModel()

To use injected view models with parameters write for example -
val profileViewModel: ProfileViewModel = koinViewModel{ parametersOf(navController)}
 */

val viewModelModule = module {
    viewModelOf(::StartScreenViewModel)
    viewModelOf(::UserAgreementViewModel)
    viewModelOf(::FaqViewModel)
    viewModelOf(::ContactViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::RuleDetailsViewModel)
    viewModelOf(::RulesViewModel)
    viewModelOf(::SmsViewModel)
    viewModelOf(::EditProfileViewModel)
}

