package com.example.fitjournal.core.domain.model

import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
// domain model used as a middle man between data and ui layer
sealed class WorkoutPropertiesModel {
    data class WeightLiftingProps(
        val props: List<WeightLiftingModel>
    ) : WorkoutPropertiesModel()
    data class CardioProps(
        val props: List<CardioModel>
    ) : WorkoutPropertiesModel()

    data class CalisthenicsProps(
        val props: List<CalisthenicsModel>
    ) : WorkoutPropertiesModel()

    fun getWeightLiftingProps(): List<WeightLiftingModel> {
        return when (this) {
            is WeightLiftingProps -> props
            else -> emptyList()
        }
    }

    fun getCardioProps(): List<CardioModel> {
        return when (this) {
            is CardioProps -> props
            else -> emptyList()
        }
    }
    fun getCalisthenicsProps(): List<CalisthenicsModel> {
        return when (this) {
            is CalisthenicsProps -> props
            else -> emptyList()
        }
    }
}

data class WeightLiftingModel(
    val reps: Int,
    val sets: Int,
    val weight: Double
)
data class CardioModel(
    val distance: Double,
    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val time: String,
    val laps: Double?
)

data class CalisthenicsModel(
    val reps: Int,
    val sets: Int,
    val time: String?,
    val weight: Double? = null
)
