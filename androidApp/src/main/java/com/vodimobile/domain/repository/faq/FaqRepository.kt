package com.vodimobile.domain.repository.faq

import com.vodimobile.domain.model.FaqModel

interface FaqRepository {
    fun getFaqList(): List<FaqModel>
}