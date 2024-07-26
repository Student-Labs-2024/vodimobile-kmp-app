package com.vodimobile.data.repository.faq

import android.content.Context
import com.vodimobile.android.R
import com.vodimobile.domain.model.FaqModel
import com.vodimobile.domain.repository.faq.FaqRepository

class FaqRepositoryImpl(private val context: Context) : FaqRepository {
    override fun getFaqList(): List<FaqModel> {
        val questions = context.resources.getStringArray(R.array.questions)
        val answers = context.resources.getStringArray(R.array.answers)


        if (questions.size != answers.size) {
            throw IllegalArgumentException("The number of questions and answers must be equal")
        }


        val faqList = mutableListOf<FaqModel>()
        for (i in answers.indices) {
            faqList.add(
                FaqModel(
                    question = questions[i],
                    answer = answers[i]
                )
            )
        }
        return faqList
    }
}