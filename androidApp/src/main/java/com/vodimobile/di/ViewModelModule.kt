package com.vodimobile.di

import com.vodimobile.presentation.screens.start_screen.StartScreenViewModel
import com.vodimobile.presentation.screens.user_agreement.UserAgreementViewModel
import com.vodimobile.presentation.screens.faq.FaqViewModel
import com.vodimobile.presentation.screens.contact.ContactViewModel
import com.vodimobile.presentation.screens.profile.ProfileViewModel
import com.vodimobile.presentation.screens.registration.RegistrationScreenViewModel
import com.vodimobile.presentation.screens.rule_details.RulesDetailsViewModel
import com.vodimobile.presentation.screens.rules.RuleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
To use injected view models write for example -
val startScreenViewModel: StartScreenViewModel by viewModel()

To use injected view models with parameters write for example -
val profileViewModel: ProfileViewModel = koinViewModel{ parametersOf(navController)}
 */

val viewModelModule = module {
    viewModelOf(::StartScreenViewModel)
    viewModelOf(::UserAgreementViewModel)
    viewModelOf(::FaqViewModel)
    viewModelOf(::ContactViewModel)

    viewModel { parameters -> ProfileViewModel(output = parameters.get()) }
    viewModel { parameters -> RegistrationScreenViewModel(output = parameters.get()) }
    viewModel { parameters -> RulesDetailsViewModel(output = parameters.get()) }
    viewModel { parameters -> RuleViewModel(output = parameters.get()) }
    viewModel { parameters -> ContactViewModel(output = parameters.get()) }
}

