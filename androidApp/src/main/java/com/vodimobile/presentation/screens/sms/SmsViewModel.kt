package com.vodimobile.presentation.screens.sms

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodimobile.App
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
                val isCodeCorrect: Boolean = isCodeCorrect(
                    generatedCode = smsState.value.code,
                    userCode = smsState.value.userCode
                )

                Log.d(
                    "TAG",
                    smsState.value.code.toString() + "   " + smsState.value.userCode.toString()
                )

                smsState.update {
                    it.copy(
                        isIncorrectCode = isCodeCorrect
                    )
                }
                if (isCodeCorrect) {
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

                if (list.size < 4) {
                    list.add(index = intent.index, element = intent.partCode)
                } else {
                    list.set(index = intent.index, element = intent.partCode)
                }
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
            smsState.update {
                it.copy(
                    code = phoneCodeGenerator()
                )
            }
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
        val smsManager: SmsManager =
            context.getSystemService(SmsManager::class.java)

        val msg = smsState.value.code.toString()
        val sentPI: PendingIntent = PendingIntent.getBroadcast(
            App.INSTANCE,
            0,
            Intent("SMS_SENT"),
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        Log.i("SMS_CODE", smsState.value.code.toString())

        smsManager.sendTextMessage(phone, null, msg, sentPI, null)
    }
}