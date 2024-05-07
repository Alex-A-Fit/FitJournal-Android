package com.example.fitjournal.home.presentation.model.ui

import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

data class CardioUi(
    val name: String,
    val icon: Int,
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val distance: Double? = null,
    val time: String? = null,
    val laps: Double? = null
) {
    fun doesCardioPropertyExist(): Boolean {
        return distance != null && time != null
    }
}
