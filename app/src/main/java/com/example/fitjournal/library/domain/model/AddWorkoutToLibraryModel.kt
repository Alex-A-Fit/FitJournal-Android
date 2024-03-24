package com.example.fitjournal.library.domain.model

import com.example.fitjournal.home.presentation.model.enum.WorkoutTypeEnum

data class AddWorkoutToLibraryModel(
    val workoutName: String,
    val workoutType: WorkoutTypeEnum,
    val snackBarMessageId: Int
)
