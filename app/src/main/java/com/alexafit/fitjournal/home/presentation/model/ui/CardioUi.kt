package com.alexafit.fitjournal.home.presentation.model.ui

import com.alexafit.fitjournal.core.domain.model.TimeModel
import com.alexafit.fitjournal.home.presentation.model.enum.CardioDistanceType

data class CardioUi(
    val name: String,
    val icon: Int,
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val distance: Double? = null,
    val distanceInKm: Double? = null,
    val time: TimeModel? = null,
    val laps: Double? = null
) {
    fun doesCardioPropertyExist(): Boolean {
        return distance != null && time != null
    }
}
