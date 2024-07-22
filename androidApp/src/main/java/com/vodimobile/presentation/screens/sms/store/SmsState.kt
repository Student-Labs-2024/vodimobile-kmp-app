package com.vodimobile.presentation.screens.sms.store

data class SmsState(
    val phoneNumber: String = "",
    val code: List<Int> = emptyList(),
    val isIncorrectCode: Boolean = false,
    val userCode: MutableList<Int> = mutableListOf(),
    val countDownAgain: Int = 0
)
