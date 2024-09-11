package com.vodimobile.di

import com.vodimobile.presentation.utils.validator.NameValidator
import com.vodimobile.presentation.utils.validator.PasswordValidator
import com.vodimobile.presentation.utils.validator.PhoneNumberValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val validatorModule = module {
    singleOf(::PhoneNumberValidator)
    singleOf(::PasswordValidator)
    singleOf(::NameValidator)
}
