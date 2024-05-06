package com.example.fitjournal.home.presentation.model.events

import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
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

    data class AddNewWeightTrainingItem(
        val newWeightLiftingItem: WeightLiftingModel,
        val workoutType: String,
        val workoutModel: WorkoutModel
    ) : EditWorkoutEvents()
}
