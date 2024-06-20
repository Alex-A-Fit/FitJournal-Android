package com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model

import com.alexafit.fitjournal.core.domain.model.CalisthenicsModel
import com.alexafit.fitjournal.core.domain.model.TimeModel
import com.alexafit.fitjournal.core.util.extensions.toIntOrZero
import com.alexafit.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

data class EditWorkoutSetCalisthenicsModel(
    val index: Int,
    val reps: String = "",
    val sets: String = "",
    val weight: String = "",
    val weightType: WeightLiftingWeightType = WeightLiftingWeightType.POUNDS,
    val hr: String = "",
    val min: String = "",
    val sec: String = ""
)

fun EditWorkoutSetCalisthenicsModel.toCalisthenicsModel(): CalisthenicsModel {
    return CalisthenicsModel(
        reps = reps.toIntOrZero(),
        sets = sets.toIntOrZero(),
        time = if (hr.isEmpty() && min.isEmpty() && sec.isEmpty()) {
            null
        } else {
            TimeModel(
                hours = hr.ifEmpty { "00" },
                minutes = min.ifEmpty { "00" },
                seconds = sec.ifEmpty { "00" }
            )
        },
        weight = weight.toDoubleOrNull(),
        weightType = weightType
    )
}
