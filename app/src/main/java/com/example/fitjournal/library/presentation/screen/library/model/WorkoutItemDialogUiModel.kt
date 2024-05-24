package com.example.fitjournal.library.presentation.screen.library.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutItemDialogUiModel(
    val workoutName: String = "",
    val workoutType: WorkoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING
)
