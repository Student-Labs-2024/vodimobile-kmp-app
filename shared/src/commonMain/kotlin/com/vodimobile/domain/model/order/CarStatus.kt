package com.vodimobile.domain.model.order


sealed class CarStatus(
    val title: String,
) {
    object Approved : CarStatus(
        title = stringResource(R.string.approved_order),
    )

    object Processing : CarStatus(
        title = stringResource(R.string.processing_order),
    )

    object Completed : CarStatus(
        title = stringResource(R.string.completed_order),
    )

    object Cancelled : CarStatus(
        title = stringResource(R.string.cancelled_order),
    )
}
