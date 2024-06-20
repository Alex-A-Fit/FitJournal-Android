package com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model

import com.alexafit.fitjournal.core.domain.model.CardioModel
import com.alexafit.fitjournal.core.domain.model.TimeModel
import com.alexafit.fitjournal.core.util.extensions.toDoubleOrZero
import com.alexafit.fitjournal.home.presentation.model.enum.CardioDistanceType

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
            hours = hr.ifEmpty { "00" },
            minutes = min.ifEmpty { "00" },
            seconds = sec.ifEmpty { "00" }
        ),
        laps = laps.toDoubleOrNull()
    )
}
