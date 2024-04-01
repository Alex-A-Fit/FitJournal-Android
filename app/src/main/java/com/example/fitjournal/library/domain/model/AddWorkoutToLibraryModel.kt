package com.example.fitjournal.library.domain.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class AddWorkoutToLibraryModel(
    val workoutName: String,
    val workoutType: WorkoutTypeEnum,
    val snackBarMessageId: Int
)
