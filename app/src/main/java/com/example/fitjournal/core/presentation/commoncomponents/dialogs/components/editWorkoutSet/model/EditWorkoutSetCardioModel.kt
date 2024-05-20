package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model

import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

data class EditWorkoutSetCardioModel(
    val index: Int,
    val laps: String = "",
    val distance: String = "",
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val hr: String = "",
    val min: String = "",
    val sec: String = ""
)

fun EditWorkoutSetCardioModel.toCardioModel(): CardioModel {
    return CardioModel(
        distance = distance.toDoubleOrZero(),
        distanceType = distanceType,
        time = TimeModel(
            hours = hr,
            minutes = min,
            seconds = sec
        ),
        laps = laps.toDoubleOrNull()
    )
}
