package com.vodimobile.domain.model.remote.either

sealed class CrmEither<out D, out E>
{
    data class CrmError<out D, out E>(val status: E) : CrmEither<D, E>()
    data object CrmLoading : CrmEither<Nothing, Nothing>()
    data class CrmData<out D, out E>(val data: D) : CrmEither<D, E>()
}