package com.vodimobile.presentation.screens.reservation.utils

import com.vodimobile.App
import com.vodimobile.android.R

data class DescribableItem(
    val title: String,
    val cost: Float
)

fun createFullFieldDescription(item: DescribableItem): String {
    val title = item.title
    val cost = item.cost.toInt()
    val resultCost =
        if (cost == 0) App.INSTANCE.getString(R.string.cost_free) else App.INSTANCE.getString(
            R.string.coast_order, cost.toString()
        )
    return "$title - $resultCost"
}

fun finalCost(cost: Double): String {
    val costToInt = cost.toInt()
    val resultCost =
        if (costToInt == 0) App.INSTANCE.getString(R.string.cost_free) else "+" + App.INSTANCE.getString(
            R.string.coast_order, costToInt.toString()
        )
    return resultCost
}
