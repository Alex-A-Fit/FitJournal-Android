package com.example.fitjournal.home.presentation.model.ui

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import org.mongodb.kbson.ObjectId

data class WorkoutUiModel(
    val workoutType: WorkoutTypeEnum,
    val date: String,
    val exerciseCardModel: CardUiModel
)

data class CardUiModel(
    val id: ObjectId,
    val name: String,
    val icon: Int,
    val reps: Int? = null,
    val weight: Double? = null,
    val time: String? = null,
    val distance: Double? = null,
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val laps: Double? = null
)
