package com.example.fitjournal.core.domain.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutLibraryModel(
    val name: String,
    val workoutType: String,
    val workoutTypeEnum: WorkoutTypeEnum
)
