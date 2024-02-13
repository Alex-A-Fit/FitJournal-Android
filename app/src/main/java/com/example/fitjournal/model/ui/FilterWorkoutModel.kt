package com.example.fitjournal.model.ui

import com.example.fitjournal.model.enum.WorkoutTypeEnum

data class FilterWorkoutModel(
    val isWorkoutSelected: Boolean = false,
    val exerciseType: WorkoutTypeEnum
)
