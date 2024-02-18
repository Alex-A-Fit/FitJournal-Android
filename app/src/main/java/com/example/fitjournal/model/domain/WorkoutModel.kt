package com.example.fitjournal.model.domain

import com.example.fitjournal.model.enum.CardioDistanceType
import com.example.fitjournal.model.enum.WorkoutTypeEnum
import java.time.LocalDateTime

data class WorkoutModel(
    val name: String,
    val icon: Int,
    val workoutTypeEnum: WorkoutTypeEnum,
    val date: String,
    val timestamp: LocalDateTime,
    val weightLiftingModel: List<WeightLiftingModel>? = null,
    val cardioModel: List<CardioModel>? = null,
    val calisthenicsModel: List<CalisthenicsModel>? = null

)

data class WeightLiftingModel(
    val reps: Int,
    val weight: Double,
    // adding time for now in case we want to allow user to input time
    val time: String? = null
)
data class CardioModel(
    val distance: Double,
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val time: String,
    val laps: Double
)

data class CalisthenicsModel(
    val reps: Int,
    val time: String
)
