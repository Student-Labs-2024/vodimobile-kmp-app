package com.vodimobile.di

import com.vodimobile.data.repository.faq.FaqRepositoryImpl
import com.vodimobile.data.repository.rules_and_condition.RulesAndConditionRepositoryImpl
import com.vodimobile.domain.repository.faq.FaqRepository
import com.vodimobile.domain.repository.rules_and_condition.RulesAndConditionRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single { FaqRepositoryImpl(context = get()) }.bind<FaqRepository>()
    single { RulesAndConditionRepositoryImpl(context = get()) }.bind<RulesAndConditionRepository>()
}
