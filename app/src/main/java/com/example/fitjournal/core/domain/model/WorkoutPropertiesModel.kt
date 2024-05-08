package com.example.fitjournal.core.domain.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
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

    fun getWeightLiftingProps(): SnapshotStateList<WeightLiftingModel> {
        return when (this) {
            is WeightLiftingProps -> props.toMutableStateList()
            else -> mutableStateListOf()
        }
    }

    fun getCardioProps(): SnapshotStateList<CardioModel> {
        return when (this) {
            is CardioProps -> props.toMutableStateList()
            else -> mutableStateListOf()
        }
    }
    fun getCalisthenicsProps(): SnapshotStateList<CalisthenicsModel> {
        return when (this) {
            is CalisthenicsProps -> props.toMutableStateList()
            else -> mutableStateListOf()
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
    val time: TimeModel?,
    val laps: Double?
)

data class CalisthenicsModel(
    val reps: Int,
    val sets: Int,
    var time: TimeModel? = null,
    val weight: Double? = null
)

data class TimeModel(
    val hours: String,
    val minutes: String,
    val seconds: String
)
