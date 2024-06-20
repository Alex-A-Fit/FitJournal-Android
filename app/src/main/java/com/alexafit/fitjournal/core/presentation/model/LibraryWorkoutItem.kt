package com.alexafit.fitjournal.core.presentation.model

import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class LibraryWorkoutItem(
    val workoutName: String,
    val workoutTypeEnum: WorkoutTypeEnum
)
