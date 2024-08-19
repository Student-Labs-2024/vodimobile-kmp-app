package com.vodimobile.presentation.utils

fun splitTextByDelimiter(text: String, delimiter: Char): Pair<String, String> {
    val newlineIndex = text.indexOf(delimiter)
    if (newlineIndex != -1) {
        val textPart1 = text.substring(0, newlineIndex)
        val textPart2 = text.substring(newlineIndex + 1)
        return Pair(textPart1, textPart2)
    } else {
        return Pair(text, "")
    }
}

fun splitTextIntoIntervalsByDelimiter(
    text: String,
    delimiter: Char,
    countWordsPart1: Int,
    countWordsPart2: Int
): Pair<String, String> {

    val words = text.split(delimiter)

    if (countWordsPart1 + countWordsPart2 == words.count()) {
        val textPart1 = words.take(countWordsPart1).joinToString(delimiter.toString())
        val textPart2 = words.takeLast(countWordsPart2).joinToString(delimiter.toString())

        return Pair(textPart1, textPart2)

    } else {
        return Pair("", "")
    }
}
