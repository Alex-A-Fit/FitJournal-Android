package com.example.fitjournal.addWorkout.model.events

import com.example.fitjournal.core.data.model.results.Result
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

sealed class AddWorkoutDetailEvents() {
    data class EditReps(
        val editWorkoutFunction: EditWorkoutFunction,
        val repValue: String
    ) : AddWorkoutDetailEvents()

    data class EditSets(
        val editWorkoutFunction: EditWorkoutFunction,
        val setValue: String
    ) : AddWorkoutDetailEvents()

    data class EditWeight(
        val editWorkoutFunction: EditWorkoutFunction,
        val weightValue: String,
        val valueDifferential: Double = 1.0
    ) : AddWorkoutDetailEvents()

    data class OnRepValueChange(
        val repValue: String
    ) : AddWorkoutDetailEvents()

    data class OnSetValueChange(
        val setValue: String
    ) : AddWorkoutDetailEvents()

    data class OnWeightValueChange(
        val weightValue: String
    ) : AddWorkoutDetailEvents()

    data class ClearWorkoutTextFields(
        val workoutTypeEnum: WorkoutTypeEnum
    ) : AddWorkoutDetailEvents()

    data class UpdateWorkoutListItem(
        val index: Int
    ) : AddWorkoutDetailEvents()

    data class DeleteWorkoutSetItemInWorkoutModelList(
        val index: Int,
        val workoutType: WorkoutTypeEnum,
        val onDeleteErrorCallback: suspend () -> Unit
    ) : AddWorkoutDetailEvents()

    data class AddNewWeightTrainingSetToWorkout(
        val newWeightLiftingItem: WeightLiftingModel,
        val workoutType: String,
        val onAddErrorCallback: suspend () -> Unit
    ) : AddWorkoutDetailEvents()

    data class AddNewCalisthenicSetToWorkout(
        val newCalisthenicItem: CalisthenicsModel,
        val workoutType: String,
        val onAddErrorCallback: suspend () -> Unit
    ) : AddWorkoutDetailEvents()

    data class EditTime(
        val value: String,
        val timeDeterminate: EditWorkoutTimeDeterminate
    ) : AddWorkoutDetailEvents()

    data class EditLaps(
        val editWorkoutFunction: EditWorkoutFunction,
        val value: String
    ) : AddWorkoutDetailEvents()

    data class OnLapsValueChange(
        val lapValue: String
    ) : AddWorkoutDetailEvents()

    data class EditDistance(
        val editWorkoutFunction: EditWorkoutFunction,
        val value: String
    ) : AddWorkoutDetailEvents()

    data object EditDistanceType : AddWorkoutDetailEvents()
    data object EditWeightType : AddWorkoutDetailEvents()

    data class OnDistanceValueChange(
        val distanceValue: String
    ) : AddWorkoutDetailEvents()
    data class AddNewCardioSetToWorkout(
        val newCardioItem: CardioModel,
        val workoutType: String,
        val onAddErrorCallback: suspend () -> Unit
    ) : AddWorkoutDetailEvents()

    data object ClearViewModelState : AddWorkoutDetailEvents()
    data object StopAddWorkoutJob : AddWorkoutDetailEvents()
    data class AddWorkoutToRealm(val callback: (Result) -> Unit) : AddWorkoutDetailEvents()
}
