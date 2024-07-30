package com.vodimobile.data.repository.crm

object CrmRouting {

    object Cars {
        const val ALL_CARS = "car_list"
    }

    object Tariff {
        const val TARIFFS_BY_CAR_ID = "tariff_list"

        object PARAM {
            const val TARIFF_BY_CAR_ID_PARAM_CAR_ID = "car_id"
        }
    }
}