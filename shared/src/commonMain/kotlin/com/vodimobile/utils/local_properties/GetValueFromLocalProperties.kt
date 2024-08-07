package com.vodimobile.utils.local_properties

import com.vodimobile.domain.model.crm.CrmServerData
import com.vodimobile.shared.buildkonfig.SharedBuildkonfig

internal fun getCrmServerDataFromLocalProperties(): CrmServerData {
    val crmServerData = CrmServerData(
        server = SharedBuildkonfig.crm_server,
        port = SharedBuildkonfig.crm_port
    )
    return crmServerData
}