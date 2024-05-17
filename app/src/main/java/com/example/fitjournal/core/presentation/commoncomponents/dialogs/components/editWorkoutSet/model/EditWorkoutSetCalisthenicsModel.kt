package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model

import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

data class EditWorkoutSetCalisthenicsModel(
    val reps: String = "",
    val sets: String = "",
    val weight: String = "",
    val weightType: WeightLiftingWeightType = WeightLiftingWeightType.POUNDS,
    val hr: String = "",
    val min: String = "",
    val sec: String = "",
    val isRepsErrorVisible: Boolean = false,
    val isSetsErrorVisible: Boolean = false,
    val isWeightErrorVisible: Boolean = false,
    val isTimeErrorVisible: Boolean = false,
    val editReps: (EditWorkoutFunction, String) -> Unit = { _, _ -> },
    val editSets: (EditWorkoutFunction, String) -> Unit = { _, _ -> },
    val editWeight: (EditWorkoutFunction, String) -> Unit = { _, _ -> },
    val editTime: (String, EditWorkoutTimeDeterminate) -> Unit = { _, _ -> }
)
