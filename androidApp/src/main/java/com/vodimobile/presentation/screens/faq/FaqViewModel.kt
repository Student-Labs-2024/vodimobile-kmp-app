package com.vodimobile.presentation.screens.faq

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.domain.repository.faq.FaqRepository
import com.vodimobile.presentation.screens.faq.store.FaqEffect
import com.vodimobile.presentation.screens.faq.store.FaqIntent
import com.vodimobile.presentation.screens.faq.store.FaqState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FaqViewModel(
    faqRepository: FaqRepository
) : ViewModel() {

    val faqState = MutableStateFlow(FaqState(faqList = faqRepository.getFaqList()))
    val faqEffect = MutableSharedFlow<FaqEffect>()

    fun onIntent(intent: FaqIntent) {
        when (intent) {
            FaqIntent.BackClick -> {
                viewModelScope.launch {
                    faqEffect.emit(FaqEffect.BackClick)
                }
            }
        }
    }
}