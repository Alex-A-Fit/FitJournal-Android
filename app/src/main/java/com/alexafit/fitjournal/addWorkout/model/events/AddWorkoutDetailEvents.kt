package com.alexafit.fitjournal.addWorkout.model.events

import com.alexafit.fitjournal.core.data.model.results.Result
import com.alexafit.fitjournal.core.domain.model.CalisthenicsModel
import com.alexafit.fitjournal.core.domain.model.CardioModel
import com.alexafit.fitjournal.core.domain.model.WeightLiftingModel
import com.alexafit.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

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

    data class CreateModelForEditWorkoutDialog(
        val workoutTypeEnum: WorkoutTypeEnum,
        val workoutPropertiesModel: WorkoutPropertiesModel,
        val index: Int,
        val getWorkoutSetCallback: (WorkoutTypeDialog) -> Unit
    ) : AddWorkoutDetailEvents()

    data class UpdateWorkoutProperties(
        val workoutTypeDialog: WorkoutTypeDialog
    ) : AddWorkoutDetailEvents()

    data object ClearViewModelState : AddWorkoutDetailEvents()
    data object StopAddWorkoutJob : AddWorkoutDetailEvents()
    data class AddWorkoutToRealm(val callback: (Result) -> Unit) : AddWorkoutDetailEvents()
    data class SelectDateFromDatePicker(
        val userSelectedDate: Long
    ) : AddWorkoutDetailEvents()
}
