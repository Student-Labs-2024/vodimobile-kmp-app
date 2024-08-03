package com.vodimobile.domain.model

data class Bid(
    val cost: Double,
    val prepay: Double,
    val deposit: Double,
    val errorMessage: String?
)
