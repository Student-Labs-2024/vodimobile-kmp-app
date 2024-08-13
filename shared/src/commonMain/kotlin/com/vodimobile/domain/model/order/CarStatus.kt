package com.vodimobile.domain.model.order

import com.vodimobile.shared.resources.SharedRes
import dev.icerock.moko.resources.StringResource


sealed class CarStatus(
    val title: StringResource,
) {
    object Approved : CarStatus(
        title = SharedRes.strings.approved_order)

    object Processing : CarStatus(
        title = SharedRes.strings.processing_order)

    object Completed : CarStatus(
        title = SharedRes.strings.completed_order)


    object Cancelled : CarStatus(
        title = SharedRes.strings.cancelled_order)
}
