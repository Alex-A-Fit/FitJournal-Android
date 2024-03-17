package com.example.fitjournal.home.presentation.model.ui

import com.example.fitjournal.home.presentation.model.enum.WorkoutTypeEnum

data class FilterWorkoutUiModel(
    val isWorkoutSelected: Boolean = false,
    val exerciseType: WorkoutTypeEnum
)
