package com.vodimobile.utils.bid

import com.vodimobile.domain.model.order.CarStatus

val bidGrip = mapOf(
    "В обработке" to CarStatus.Processing,
    "Одобрено" to CarStatus.Approved,
    "Завершено" to CarStatus.Completed,
    "Отменено" to CarStatus.Cancelled,
    "Создана бронь" to CarStatus.Reserve,
)

val bidGripReverse = mapOf(
    CarStatus.Processing to "В обработке",
    CarStatus.Approved to "Одобрено",
    CarStatus.Completed to "Завершено",
    CarStatus.Cancelled to "Отменено",
    CarStatus.Reserve to "Создана бронь",
)

val crmBidGrid = mapOf(
    "new" to CarStatus.Processing,

    "accepted_as_reserve" to CarStatus.Reserve,

    "accepted_as_rent" to CarStatus.Approved,

    "not_found" to CarStatus.Cancelled,
    "rejected" to CarStatus.Cancelled,
    "rejected" to CarStatus.Cancelled,
    "deleted" to CarStatus.Cancelled,
)
