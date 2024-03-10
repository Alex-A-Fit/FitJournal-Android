package com.example.fitjournal.model.ui

import com.example.fitjournal.model.enum.CardioDistanceType
import com.example.fitjournal.model.enum.WorkoutTypeEnum

data class WorkoutUiModel(
    val workoutType: WorkoutTypeEnum,
    val exerciseCardModel: CardUiModel
)

data class CardUiModel(
    val name: String,
    val icon: Int,
    val reps: Int? = null,
    val weight: Double? = null,
    val time: String? = null,
    val distance: Double? = null,
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val laps: Double? = null
)
