package com.vodimobile.presentation.utils

fun splitAgreementText(agreementText: String, delimiterChar: Char): Pair<String, String> {

    val newlineIndex = agreementText.indexOf(delimiterChar)
    val agreementTextPart1 = agreementText.substring(0, newlineIndex)
    val agreementTextPart2 = agreementText.substring(newlineIndex + 1)

    return Pair(agreementTextPart1, agreementTextPart2)
}
