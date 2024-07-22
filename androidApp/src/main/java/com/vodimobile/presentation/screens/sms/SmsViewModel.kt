package com.vodimobile.presentation.screens.sms

import android.content.Context
import android.os.Build
import android.telephony.SmsManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.android.R
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

            is SmsIntent.SendSmsCode -> {
                sendSms(phone = intent.phone, context = intent.context)
            }

            is SmsIntent.OnInputPartCode -> {
                val list = smsState.value.userCode
                list.add(intent.partCode)
                smsState.update {
                    it.copy(
                        userCode = list
                    )
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

    private fun sendSms(context: Context, phone: String) {
        val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getSystemService(SmsManager::class.java)
        } else {
            SmsManager.getDefault()
        }

        val msg = context.resources.getString(R.string.code, smsState.value.code)

        smsManager.sendTextMessage(phone, null, msg, null, null)
    }
}