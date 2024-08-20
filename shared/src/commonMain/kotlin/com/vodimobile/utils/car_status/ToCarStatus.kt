package com.vodimobile.utils.car_status

import com.vodimobile.domain.model.order.CarStatus

fun String.toCarStatus(): CarStatus {
    when (this) {
        "Одобрено" -> {
            return CarStatus.Approved
        }

        "В обработке" -> {
            return CarStatus.Processing
        }

        "Завершено" -> {
            return CarStatus.Completed
        }

        "Отменено" -> {
            return CarStatus.Cancelled
        }

        "Создана бронь" -> {
            return CarStatus.Reserve
        }

        else -> {
            return CarStatus.Completed
        }
    }
}