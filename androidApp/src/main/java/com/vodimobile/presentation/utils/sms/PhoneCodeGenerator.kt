package com.vodimobile.presentation.utils.sms

import kotlin.random.Random

fun phoneCodeGenerator(): List<Int> {
    val randomNumbers = mutableListOf<Int>()
    for (i in 1..4) {
        randomNumbers.add(Random.nextInt(1, 10))
    }
    return randomNumbers
}