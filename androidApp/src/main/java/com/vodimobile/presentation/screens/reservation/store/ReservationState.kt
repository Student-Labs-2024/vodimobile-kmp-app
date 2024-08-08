package com.vodimobile.presentation.screens.reservation.store

import com.vodimobile.domain.model.Bid
import com.vodimobile.domain.model.Car
import com.vodimobile.domain.model.Place
import com.vodimobile.domain.model.remote.either.CrmEither
import io.ktor.http.HttpStatusCode

data class ReservationState(
    val places: CrmEither<List<Place>, HttpStatusCode> = CrmEither.CrmLoading,
    val bidCost: CrmEither<Bid, HttpStatusCode> = CrmEither.CrmLoading,
    val place: String = "",
    val placeId: Int = 0,
    val date: LongArray = longArrayOf(System.currentTimeMillis(), System.currentTimeMillis()),
    val time: String = "",
    val errorTime: Boolean = false,
    val comments: String = "",
    val car: Car = Car.empty(),
)
