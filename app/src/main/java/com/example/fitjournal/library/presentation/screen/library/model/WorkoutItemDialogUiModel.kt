package com.example.fitjournal.library.presentation.screen.library.model

import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutItemDialogUiModel(
    val libraryWorkoutItem: LibraryWorkoutItem = LibraryWorkoutItem(
        workoutName = "",
        workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING
    ),
    val workoutCategoryIndex: Int = 0
)
