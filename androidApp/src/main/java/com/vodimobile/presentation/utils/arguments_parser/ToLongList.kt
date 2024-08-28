package com.vodimobile.presentation.utils.arguments_parser

fun List<Pair<Long, Long>>.pairListToLongList(): List<Long> {
    return this.flatMap { (first, second) -> listOf(first, second) }
}

