package com.vodimobile.utils.array_parser

fun String.toStringArray(): Array<String> {
    return this.substring(1, this.length - 1)
        .split(",")
        .toTypedArray()
}