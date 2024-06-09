package com.example.fitjournal.library.presentation.model

import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutItemDialogUiModel(
    val libraryWorkoutItem: LibraryWorkoutItem = LibraryWorkoutItem(
        workoutName = "",
        workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING
    ),
    val workoutCategoryIndex: Int = 0,

    // if user updates a workout, new values will be placed here
    val newWorkoutName: String = "",
    val newWorkoutType: WorkoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING

)
