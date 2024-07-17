package com.vodimobile.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.vodimobile.presentation.utils.EmailValidator
import com.vodimobile.presentation.utils.PhoneNumberValidator

val validatorModule = module {
    singleOf(::EmailValidator)
    singleOf(::PhoneNumberValidator)
}