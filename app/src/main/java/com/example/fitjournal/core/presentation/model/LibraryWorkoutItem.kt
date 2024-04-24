package com.example.fitjournal.core.presentation.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class LibraryWorkoutItem(
    val workoutName: String,
    val workoutTypeEnum: WorkoutTypeEnum
)
