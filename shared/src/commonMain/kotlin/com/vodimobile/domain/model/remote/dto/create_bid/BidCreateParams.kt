package com.vodimobile.domain.model.remote.dto.create_bid

import okio.Path

class BidCreateParams(
    val fio: String,
    val phone: String,
    val car_id: Int,
    val begin: String,
    val end: String,
    val begin_place_id: Int,
    val end_place_id: Int,
    val services: List<Int>? = null,
    val prepayment: Double? = null,
    val files: List<Path>? = null
)