package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model

import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

data class EditWorkoutSetWeightLiftingModel(
    val index: Int,
    val reps: String = "",
    val sets: String = "",
    val weight: String = "",
    val weightType: WeightLiftingWeightType = WeightLiftingWeightType.POUNDS
)

fun EditWorkoutSetWeightLiftingModel.toWeightLiftingModel(): WeightLiftingModel {
    return WeightLiftingModel(
        reps = reps.toIntOrZero(),
        sets = sets.toIntOrZero(),
        weight = weight.toDoubleOrZero(),
        weightType = weightType
    )
}
