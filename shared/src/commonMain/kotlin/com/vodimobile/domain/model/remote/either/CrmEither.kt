package com.vodimobile.domain.model.remote.either

data class CrmEither<out D, out E>(
    val data: D?,
    val status: E,
)