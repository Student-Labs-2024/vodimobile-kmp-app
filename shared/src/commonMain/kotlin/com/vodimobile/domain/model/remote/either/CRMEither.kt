package com.vodimobile.domain.model.remote.either

data class CRMEither<out D, out E>(
    val data: D,
    val httpStatusCode: E,
)