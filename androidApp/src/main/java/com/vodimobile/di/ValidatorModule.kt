package com.vodimobile.di

import com.vodimobile.presentation.utils.NameValidator
import com.vodimobile.presentation.utils.PasswordValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val validatorModule = module {
    singleOf(::PhoneNumberValidator)
    singleOf(::PasswordValidator)
    singleOf(::NameValidator)
}
