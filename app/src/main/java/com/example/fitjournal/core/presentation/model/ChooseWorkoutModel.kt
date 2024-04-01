package com.example.fitjournal.core.presentation.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class ChooseWorkoutModel(
    val workoutName: String,
    val workoutType: WorkoutTypeEnum
)
