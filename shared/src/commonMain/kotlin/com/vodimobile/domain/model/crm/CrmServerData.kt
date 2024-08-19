package com.vodimobile.domain.model.crm

data class CrmServerData(
    val server: String,
    val port: String
) {

    override fun toString(): String {
        return "$server:$port"
    }

    companion object {
        fun CrmServerData.buildUrl(end: String): String {
            return "http://${this}/data_api/$end"
        }
    }
}
