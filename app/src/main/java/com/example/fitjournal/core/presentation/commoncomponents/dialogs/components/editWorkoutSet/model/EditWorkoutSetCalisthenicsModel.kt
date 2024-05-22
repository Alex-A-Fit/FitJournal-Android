package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model

import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

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
        time = if (hr.isEmpty() && min.isEmpty() && sec.isEmpty()) null else TimeModel(hours = hr, minutes = min, seconds = sec),
        weight = weight.toDoubleOrNull(),
        weightType = weightType
    )
}
