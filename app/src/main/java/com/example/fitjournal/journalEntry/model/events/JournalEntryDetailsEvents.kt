package com.example.fitjournal.journalEntry.model.events

import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

sealed class JournalEntryDetailsEvents() {
    data class EditReps(
        val editWorkoutFunction: EditWorkoutFunction,
        val repValue: String
    ) : JournalEntryDetailsEvents()

    data class EditSets(
        val editWorkoutFunction: EditWorkoutFunction,
        val setValue: String
    ) : JournalEntryDetailsEvents()

    data class EditWeight(
        val editWorkoutFunction: EditWorkoutFunction,
        val weightValue: String,
        val valueDifferential: Double = 1.0
    ) : JournalEntryDetailsEvents()

    data class OnRepValueChange(
        val repValue: String
    ) : JournalEntryDetailsEvents()

    data class OnSetValueChange(
        val setValue: String
    ) : JournalEntryDetailsEvents()

    data class OnWeightValueChange(
        val weightValue: String
    ) : JournalEntryDetailsEvents()

    data class ClearWorkoutTextFields(
        val workoutTypeEnum: WorkoutTypeEnum
    ) : JournalEntryDetailsEvents()

    data class UpdateWorkoutListItem(
        val index: Int
    ) : JournalEntryDetailsEvents()

    data class DeleteWorkoutSetItemInWorkoutModelList(
        val index: Int,
        val workoutType: WorkoutTypeEnum,
        val onDeleteErrorCallback: suspend () -> Unit
    ) : JournalEntryDetailsEvents()

    data class AddNewWeightTrainingSetToWorkout(
        val newWeightLiftingItem: WeightLiftingModel,
        val workoutType: String,
        val onAddErrorCallback: suspend () -> Unit
    ) : JournalEntryDetailsEvents()

    data class AddNewCalisthenicSetToWorkout(
        val newCalisthenicItem: CalisthenicsModel,
        val workoutType: String,
        val onAddErrorCallback: suspend () -> Unit
    ) : JournalEntryDetailsEvents()

    data class EditTime(
        val value: String,
        val timeDeterminate: EditWorkoutTimeDeterminate
    ) : JournalEntryDetailsEvents()

    data class EditLaps(
        val editWorkoutFunction: EditWorkoutFunction,
        val value: String
    ) : JournalEntryDetailsEvents()

    data class OnLapsValueChange(
        val lapValue: String
    ) : JournalEntryDetailsEvents()

    data class EditDistance(
        val editWorkoutFunction: EditWorkoutFunction,
        val value: String
    ) : JournalEntryDetailsEvents()

    data object EditDistanceType : JournalEntryDetailsEvents()
    data object EditWeightType : JournalEntryDetailsEvents()

    data class OnDistanceValueChange(
        val distanceValue: String
    ) : JournalEntryDetailsEvents()
    data class AddNewCardioSetToWorkout(
        val newCardioItem: CardioModel,
        val workoutType: String,
        val onAddErrorCallback: suspend () -> Unit
    ) : JournalEntryDetailsEvents()

    data object ClearViewModelState : JournalEntryDetailsEvents()
}
