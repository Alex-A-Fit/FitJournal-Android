package com.alexafit.fitjournal.home.presentation.model.events

import com.alexafit.fitjournal.core.domain.model.CalisthenicsModel
import com.alexafit.fitjournal.core.domain.model.CardioModel
import com.alexafit.fitjournal.core.domain.model.WeightLiftingModel
import com.alexafit.fitjournal.core.domain.model.WorkoutModel
import com.alexafit.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

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
        val valueDifferential: Double = 1.0
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
        val workoutTypeDialog: WorkoutTypeDialog,
        val workoutModel: WorkoutModel,
        val workoutType: String,
        val onSuccessfulUpdateCallback: () -> Unit,
        val onUpdateErrorCallback: suspend () -> Unit
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

    data class GetWorkoutSet(
        val workoutTypeEnum: WorkoutTypeEnum,
        val workoutPropertiesModel: WorkoutPropertiesModel,
        val index: Int,
        val getWorkoutSetCallback: (WorkoutTypeDialog) -> Unit
    ) : EditWorkoutEvents()

    data class SelectDateFromDatePicker(
        val userSelectedDate: Long,
        val workout: WorkoutModel,
        val workoutType: String,
        val onSuccessfulUpdateCallback: suspend () -> Unit,
        val onErrorCallback: suspend () -> Unit
    ) : EditWorkoutEvents()

    data class UpdateDate(
        val date: String
    ) : EditWorkoutEvents()
}
