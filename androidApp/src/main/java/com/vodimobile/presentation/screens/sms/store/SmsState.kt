package com.vodimobile.presentation.screens.sms.store

data class SmsState(
    val phoneNumber: String = "",
    val code: List<Int> = emptyList(),
    val isIncorrectCode: Boolean = true,
    val userCode: MutableList<Int> = mutableListOf(0,0,0,0),
    val countDownAgain: Int = 0
)
