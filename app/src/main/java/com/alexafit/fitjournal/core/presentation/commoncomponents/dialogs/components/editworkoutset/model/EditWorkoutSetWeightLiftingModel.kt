package com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model

import com.alexafit.fitjournal.core.domain.model.WeightLiftingModel
import com.alexafit.fitjournal.core.util.extensions.toDoubleOrZero
import com.alexafit.fitjournal.core.util.extensions.toIntOrZero
import com.alexafit.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

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
