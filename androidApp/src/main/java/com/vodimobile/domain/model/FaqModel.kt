package com.vodimobile.domain.model

import android.content.res.Resources
import com.vodimobile.android.R

data class FaqModel(
    val question: String,
    val answer: String
) {

    companion object {
        fun getFaqList(resources: Resources): List<FaqModel> {

            val questions = resources.getStringArray(R.array.questions)
            val answers = resources.getStringArray(R.array.answers)


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
}
