package com.example.fitjournal.core.presentation.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

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
    val time: String? = null,
    val distance: Double? = null,
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val laps: Double? = null
)
