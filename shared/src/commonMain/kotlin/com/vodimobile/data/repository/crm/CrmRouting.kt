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

    object UserAuth {
        const val USER_AUTH = "api/v1/tokens/signin"
    }

    object Places {
        const val ALL_PLACES = "place_list"
    }

    object CarFreeList {
        const val CAR_FREE_LIST = "car_free_list"

        object PARAM {
            const val BEGIN: String = "begin"
            const val END: String = "end"
            const val INCLUDE_RESERVES: String = "include_reserves"
            const val INCLUDE_IDLES: String = "include_idles"
            const val CITY_ID: String = "city_id"
        }
    }
}