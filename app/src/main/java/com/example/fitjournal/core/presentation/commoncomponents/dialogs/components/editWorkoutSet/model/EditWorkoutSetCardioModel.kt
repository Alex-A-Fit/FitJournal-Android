package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model

import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

data class EditWorkoutSetCardioModel(
    val laps: String = "",
    val distance: String = "",
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val hr: String = "",
    val min: String = "",
    val sec: String = "",
    val isLapErrorVisible: Boolean = false,
    val isDistanceErrorVisible: Boolean = false,
    val isTimeErrorVisible: Boolean = false,
    val editLaps: (EditWorkoutFunction, String) -> Unit = { _, _ -> },
    val editDistance: (EditWorkoutFunction, String) -> Unit = { _, _ -> },
    val editTime: (String, EditWorkoutTimeDeterminate) -> Unit = { _, _ -> }
)
