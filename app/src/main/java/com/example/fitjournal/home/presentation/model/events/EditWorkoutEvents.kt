package com.example.fitjournal.home.presentation.model.events

import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

sealed class EditWorkoutEvents {
    data class EditReps(
        val editWorkoutFunction: EditWorkoutFunction,
        val repValue: String
    ) : EditWorkoutEvents()

    data class EditSets(
        val editWorkoutFunction: EditWorkoutFunction,
        val setValue: String
    ) : EditWorkoutEvents()

    data class EditWeight(
        val editWorkoutFunction: EditWorkoutFunction,
        val weightValue: String,
        val valueDifferential: Double
    ) : EditWorkoutEvents()

    data class OnRepValueChange(
        val repValue: String
    ) : EditWorkoutEvents()

    data class OnSetValueChange(
        val setValue: String
    ) : EditWorkoutEvents()

    data class OnWeightValueChange(
        val weightValue: String
    ) : EditWorkoutEvents()

    data class ClearWorkoutTextFields(
        val workoutTypeEnum: WorkoutTypeEnum
    ) : EditWorkoutEvents()

    data class UpdateWorkoutListItem(
        val index: Int
    ) : EditWorkoutEvents()

    data class DeleteWorkoutSetItemInWorkoutModelList(
        val index: Int,
        val workoutType: String,
        val workoutModel: WorkoutModel,
        val onDeleteErrorCallback: suspend () -> Unit
    ) : EditWorkoutEvents()

    data class DeleteEntireWorkout(
        val workoutId: String,
        val onDeleteErrorCallback: suspend () -> Unit,
        val onSuccessfulDeleteCallback: () -> Unit
    ) : EditWorkoutEvents()

    data class AddNewWeightTrainingSetToWorkout(
        val newWeightLiftingItem: WeightLiftingModel,
        val workoutType: String,
        val workoutModel: WorkoutModel,
        val onAddErrorCallback: suspend () -> Unit
    ) : EditWorkoutEvents()

    data class AddNewCalisthenicSetToWorkout(
        val newCalisthenicItem: CalisthenicsModel,
        val workoutType: String,
        val workoutModel: WorkoutModel,
        val onAddErrorCallback: suspend () -> Unit
    ) : EditWorkoutEvents()

    data class EditTime(
        val value: String,
        val timeDeterminate: EditWorkoutTimeDeterminate
    ) : EditWorkoutEvents()

    data class EditLaps(
        val editWorkoutFunction: EditWorkoutFunction,
        val value: String
    ) : EditWorkoutEvents()

    data class OnLapsValueChange(
        val lapValue: String
    ) : EditWorkoutEvents()

    data class EditDistance(
        val editWorkoutFunction: EditWorkoutFunction,
        val value: String
    ) : EditWorkoutEvents()

    data object EditDistanceType : EditWorkoutEvents()
    data object EditWeightType : EditWorkoutEvents()

    data class OnDistanceValueChange(
        val distanceValue: String
    ) : EditWorkoutEvents()
    data class AddNewCardioSetToWorkout(
        val newCardioItem: CardioModel,
        val workoutType: String,
        val workoutModel: WorkoutModel,
        val onAddErrorCallback: suspend () -> Unit
    ) : EditWorkoutEvents()
}
