package com.vodimobile.presentation.utils.arguments_parser

fun LongArray.longArrayToPairList(): List<Pair<Long, Long>> {
    require(this.size % 2 == 0) { "Массив должен иметь четное количество элементов" }

    val pairs = mutableListOf<Pair<Long, Long>>()
    for (i in indices step 2) {
        pairs.add(Pair(this[i], this[i + 1]))
    }
    return pairs
}