package com.vodimobile.domain.model

import com.vodimobile.domain.model.remote.dto.place_list.PlacesDTO

data class Place(
    val placeId: Int,
    val title: String,
    val cityId: Int,
    val deliveryCost: Float,
) {
    companion object {
        fun PlacesDTO.toPlace(): Place {
            return Place(
                placeId = this.place_id,
                title = this.title,
                cityId = this.city_id,
                deliveryCost = this.delivery_cost
            )
        }
    }
}