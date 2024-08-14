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

    object RefreshToken {
        const val REFRESH_TOKEN = "api/v1/tokens/refresh"
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

    object ServiceList {
        const val SERVICE_LIST = "service_list"
    }

    object BidCost {
        const val BID_COST = "bid_cost"

        object PARAM {
            const val CAR_ID = "car_id"
            const val BEGIN = "begin"
            const val END = "end"
            const val BEGIN_PLACE_ID = "begin_place_id"
            const val END_PLACE_ID = "end_place_id"
            const val SERVICES = "services"
        }
    }

    object CarPeriodList {
        const val CAR_PERIOD = "car_period_list"

        object PARAM {
            const val CAR_ID = "car_id"
            const val BEGIN = "begin"
            const val END = "end"
            const val INCLUDE_RESERVES = "include_reserves"
            const val INCLUDE_IDLES = "include_idles"
        }
    }
}