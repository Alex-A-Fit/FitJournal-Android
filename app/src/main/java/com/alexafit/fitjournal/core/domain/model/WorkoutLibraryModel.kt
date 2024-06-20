package com.alexafit.fitjournal.core.domain.model

import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutLibraryModel(
    val name: String,
    val workoutType: String,
    val workoutTypeEnum: WorkoutTypeEnum
)
