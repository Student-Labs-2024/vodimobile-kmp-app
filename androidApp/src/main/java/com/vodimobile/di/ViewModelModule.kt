package com.vodimobile.di

import com.vodimobile.presentation.screens.authorization.AuthorizationViewModel
import com.vodimobile.presentation.screens.change_password.ChangePasswordViewModel
import com.vodimobile.presentation.screens.start_screen.StartScreenViewModel
import com.vodimobile.presentation.screens.user_agreement.UserAgreementViewModel
import com.vodimobile.presentation.screens.faq.FaqViewModel
import com.vodimobile.presentation.screens.contact.ContactViewModel
import com.vodimobile.presentation.screens.edit_profile.EditProfileViewModel
import com.vodimobile.presentation.screens.home.HomeViewModel
import com.vodimobile.presentation.screens.network_error.ConnectionErrorViewModel
import com.vodimobile.presentation.screens.profile.ProfileViewModel
import com.vodimobile.presentation.screens.registration.RegistrationViewModel
import com.vodimobile.presentation.screens.reservation.ReservationViewModel
import com.vodimobile.presentation.screens.reset_password.NewPasswordViewModel
import com.vodimobile.presentation.screens.reset_password.ResetPasswordViewModel
import com.vodimobile.presentation.screens.rule_details.RuleDetailsViewModel
import com.vodimobile.presentation.screens.rules.RulesViewModel
import com.vodimobile.presentation.screens.server_error.ServerErrorViewModel
import com.vodimobile.presentation.screens.sms.SmsViewModel
import com.vodimobile.presentation.screens.vehicle_fleet.VehicleFleetViewModel
import com.vodimobile.presentation.screens.error_app.ErrorAppViewModel
import com.vodimobile.presentation.screens.successful_app.SuccessfulAppViewModel
import com.vodimobile.presentation.screens.orders.OrderViewModel
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
    viewModelOf(::ConnectionErrorViewModel)
    viewModelOf(::ServerErrorViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ChangePasswordViewModel)
    viewModelOf(::VehicleFleetViewModel)
    viewModelOf(::AuthorizationViewModel)
    viewModelOf(::ResetPasswordViewModel)
    viewModelOf(::NewPasswordViewModel)
    viewModelOf(::ErrorAppViewModel)
    viewModelOf(::SuccessfulAppViewModel)
    viewModelOf(::ReservationViewModel)
    viewModelOf(::OrderViewModel)
}
