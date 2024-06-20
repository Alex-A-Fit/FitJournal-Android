package com.alexafit.fitjournal.core.presentation.model

import com.alexafit.fitjournal.core.domain.model.TimeModel
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutUiModel(
    val id: String,
    val date: String,
    val workoutDetailsUiModel: WorkoutDetailsUiModel
)

data class WorkoutDetailsUiModel(
    val name: String,
    val icon: Int,
    val workoutType: WorkoutTypeEnum,
    val exerciseCardModel: WorkoutPropertiesUiModel?
)

data class WorkoutPropertiesUiModel(
    val reps: Int? = null,
    val sets: Int? = null,
    val weight: Double? = null,
    val time: TimeModel? = null,
    val distance: Double? = null,
    val laps: Double? = null
)
