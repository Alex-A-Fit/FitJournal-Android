package com.alexafit.fitjournal.home.presentation.model.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alexafit.fitjournal.core.domain.model.CalisthenicsModel
import com.alexafit.fitjournal.core.domain.model.CardioModel
import com.alexafit.fitjournal.core.domain.model.WeightLiftingModel
import com.alexafit.fitjournal.core.domain.model.WorkoutModel
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.home.presentation.model.enum.CardioDistanceType
import com.alexafit.fitjournal.home.presentation.model.enum.WeightLiftingWeightType
import com.alexafit.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.alexafit.fitjournal.home.presentation.util.constants.GeneralConstants

data class EditWorkoutUiState(
    val workout: UiState<WorkoutModel> = UiState.None,
    val editWorkoutEvents: (EditWorkoutEvents) -> Unit,
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
