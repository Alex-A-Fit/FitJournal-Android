package com.alexafit.fitjournal.library.domain.model

import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class AddWorkoutToLibraryModel(
    val workoutName: String,
    val workoutType: WorkoutTypeEnum,
    val snackBarMessageId: Int
)
