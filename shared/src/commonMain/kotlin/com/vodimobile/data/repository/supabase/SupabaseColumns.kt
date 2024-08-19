package com.vodimobile.data.repository.supabase

object SupabaseColumns {
    object User {
        const val USER_ID = "user_id"
        const val PASSWORD = "password"
        const val PHONE = "phone"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val FULL_NAME = "full_name"
    }
    object Orders {
        const val BID_ID = "bid_id"
        const val USER_ID = "user_id"
        const val BID_NUMBER = "bid_number"
        const val CRM_BID_ID = "crm_bid_id"
        const val CAR_ID = "car_id"
        const val BID_STATUS = "bid_status"
        const val DATE_START = "date_start"
        const val TIME_START = "time_start"
        const val DATE_FINISH = "date_finish"
        const val TIME_FINISH = "time_finish"
        const val PLACE_START = "place_start"
        const val PLACE_FINISH = "place_finish"
        const val COST = "cost"
        const val SERVICES = "services"
    }
}