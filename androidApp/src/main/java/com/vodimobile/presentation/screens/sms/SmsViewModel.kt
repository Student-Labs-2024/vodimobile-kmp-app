package com.vodimobile.presentation.screens.sms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.presentation.screens.sms.store.SmsEffect
import com.vodimobile.presentation.screens.sms.store.SmsIntent
import com.vodimobile.presentation.screens.sms.store.SmsState
import com.vodimobile.presentation.utils.phoneCodeGenerator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SmsViewModel : ViewModel() {
    val smsState = MutableStateFlow(
        SmsState(
            code = phoneCodeGenerator()
        )
    )
    val smsEffect = MutableSharedFlow<SmsEffect>()

    fun onIntent(intent: SmsIntent) {
        when (intent) {
            SmsIntent.OnConfirmSmsCode -> {
                val isIncorrectCode: Boolean = isCodeCorrect(
                    generatedCode = smsState.value.code,
                    userCode = smsState.value.userCode
                )
                smsState.update {
                    it.copy(
                        isIncorrectCode = isIncorrectCode
                    )
                }
                if (isIncorrectCode) {
                    viewModelScope.launch {
                        smsEffect.emit(SmsEffect.SmsCodeCorrect)
                    }
                }
            }

            SmsIntent.SendSmsCodeAgain -> {
                viewModelScope.launch {
                    countDownTimer()
                }
            }
        }
    }

    private fun isCodeCorrect(generatedCode: List<Int>, userCode: List<Int>): Boolean {
        if (generatedCode.size != userCode.size) {
            return false
        }
        for (i in generatedCode.indices) {
            if (generatedCode[i] != userCode[i]) {
                return false
            }
        }
        return true
    }

    private suspend fun countDownTimer() {
        if (smsState.value.countDownAgain == 0) {
            var count = 45
            repeat(45) {
                delay(1.seconds)
                count -= 1
                smsState.update {
                    it.copy(
                        countDownAgain = count
                    )
                }
            }
        }
    }
}