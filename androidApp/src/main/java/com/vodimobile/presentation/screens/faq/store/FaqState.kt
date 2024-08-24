package com.vodimobile.presentation.screens.faq.store

import com.vodimobile.domain.model.FaqModel

data class FaqState(
    val faqList: List<FaqModel> = emptyList()
)
