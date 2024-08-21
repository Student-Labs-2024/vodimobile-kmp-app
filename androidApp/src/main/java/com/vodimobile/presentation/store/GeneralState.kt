package com.vodimobile.presentation.store

data class GeneralState(
    val selectedDate: LongArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeneralState

        return selectedDate.contentEquals(other.selectedDate)
    }

    override fun hashCode(): Int {
        return selectedDate.contentHashCode()
    }
}
