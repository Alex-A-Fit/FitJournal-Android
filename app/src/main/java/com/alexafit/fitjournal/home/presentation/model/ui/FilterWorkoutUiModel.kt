package com.alexafit.fitjournal.home.presentation.model.ui

import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class FilterWorkoutUiModel(
    var isWorkoutFilterSelected: Boolean = false,
    val exerciseType: WorkoutTypeEnum
)
