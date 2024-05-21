package com.example.fitjournal.addWorkout.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitjournal.addWorkout.model.events.AddWorkoutDetailEvents
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType
import com.example.fitjournal.home.presentation.util.constants.GeneralConstants

data class AddWorkoutDetailUiState(
    val addWorkoutDetailEvents: (AddWorkoutDetailEvents) -> Unit,
    val workoutName: String = "",
    val workoutType: String = "",
    val workoutTypeEnum: WorkoutTypeEnum? = null,
    val localDate: String = GeneralConstants.todayDate,
    val localDateInMillis: Long = GeneralConstants.todayDateTimeInMilli,

    // three lists make up the list of sets user did for each workout
    val weightLiftingPropertyList: SnapshotStateList<WeightLiftingModel> = mutableStateListOf(),
    val cardioPropertyList: SnapshotStateList<CardioModel> = mutableStateListOf(),
    val calisthenicsPropertyList: SnapshotStateList<CalisthenicsModel> = mutableStateListOf(),

    // various properties that could be updated
    val reps: String = "",
    val isRepsErrorVisible: Boolean = false,

    val sets: String = "",
    val isSetsErrorVisible: Boolean = false,

    val weight: String = "",
    val isWeightErrorVisible: Boolean = false,

    val hour: String = "",
    val minute: String = "",
    val second: String = "",
    val isTimeErrorVisible: Boolean = false,

    val laps: String = "",
    val isLapsErrorVisible: Boolean = false,

    val distance: String = "",
    val isDistanceErrorVisible: Boolean = false,

    val distanceType: CardioDistanceType = CardioDistanceType.MILES,
    val weightType: WeightLiftingWeightType = WeightLiftingWeightType.POUNDS
)
