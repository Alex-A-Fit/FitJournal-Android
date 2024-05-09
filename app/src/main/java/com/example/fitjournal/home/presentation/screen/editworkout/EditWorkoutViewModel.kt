package com.example.fitjournal.home.presentation.screen.editworkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.domain.usecase.workout.EditWorkoutUseCase
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.model.enum.EditWorkoutListFunctions
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState
import com.example.fitjournal.home.presentation.model.ui.CalisthenicsValidator
import com.example.fitjournal.home.presentation.model.ui.CardioValidator
import com.example.fitjournal.home.presentation.model.ui.WeightLiftingValidator
import com.example.fitjournal.statistics.domain.mapper.toRealmWorkoutEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditWorkoutViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase,
    private val editWorkoutUseCase: EditWorkoutUseCase
) : ViewModel() {
    var editWorkoutState: EditWorkoutUiState by mutableStateOf(
        EditWorkoutUiState(
            editWorkoutEvents = ::editWorkoutEvents
        )
    )
        private set

    private fun editWorkoutEvents(event: EditWorkoutEvents) {
        when (event) {
            is EditWorkoutEvents.EditReps -> {
                addOrSubtractReps(
                    editWorkoutFunction = event.editWorkoutFunction,
                    repValue = event.repValue
                )
            }

            is EditWorkoutEvents.EditSets -> {
                addOrSubtractSets(
                    editWorkoutFunction = event.editWorkoutFunction,
                    setValue = event.setValue
                )
            }

            is EditWorkoutEvents.EditWeight -> {
                addOrSubtractWeight(
                    editWorkoutFunction = event.editWorkoutFunction,
                    weightValue = event.weightValue,
                    valueDifferential = event.valueDifferential
                )
            }

            is EditWorkoutEvents.OnRepValueChange -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        reps = event.repValue,
                        isRepsErrorVisible = false
                    )
                )
            }

            is EditWorkoutEvents.OnSetValueChange -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        sets = event.setValue,
                        isSetsErrorVisible = false
                    )
                )
            }

            is EditWorkoutEvents.OnWeightValueChange -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        weight = event.weightValue,
                        isWeightErrorVisible = false
                    )
                )
            }

            is EditWorkoutEvents.ClearWorkoutTextFields -> {
                clearWorkoutFields(event.workoutTypeEnum)
            }

            is EditWorkoutEvents.AddNewWeightTrainingSetToWorkout -> {
                val workoutValidity = areWeightLiftingPropertiesValid()
                if (workoutValidity.isWorkoutValid()) {
                    val newPropsModel = getNewWorkoutPropertiesModel(
                        workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
                        editWorkoutFunction = EditWorkoutListFunctions.ADD_WORKOUT_ITEM,
                        newWeightLiftingItem = event.newWeightLiftingItem
                    )
                    if (newPropsModel == null) {
                        viewModelScope.launch {
                            event.onAddErrorCallback()
                        }
                        return
                    }
                    val workoutModel = event.workoutModel
                    workoutModel.workoutDetailsModel.workoutPropertiesModel = newPropsModel
                    try {
                        val updatedRealmEntry =
                            workoutModel.toRealmWorkoutEntry(
                                workoutType = event.workoutType
                            )
                        viewModelScope.launch {
                            val isUpdateSuccess = realmWorkoutEntryUseCase
                                .updateSingleWorkoutEntryToRealmDbUseCase(
                                    updatedRealmWorkoutEntry = updatedRealmEntry
                                )
                            if (isUpdateSuccess) {
                                updateWorkoutState(
                                    newEditWorkoutUiState = editWorkoutState.copy(
                                        weightLiftingPropertyList = newPropsModel.getWeightLiftingProps(),
                                        reps = "",
                                        sets = "",
                                        weight = ""
                                    )
                                )
                            } else {
                                event.onAddErrorCallback()
                            }
                        }
                    } catch (e: Exception) {
                        viewModelScope.launch {
                            event.onAddErrorCallback()
                        }
                    }
                }
            }

            is EditWorkoutEvents.DeleteWorkoutSetItemInWorkoutModelList -> {
                val workoutModel = event.workoutModel
                try {
                    val workoutType = workoutModel.workoutDetailsModel.workoutTypeEnum
                    val newPropertiesModel = getNewWorkoutPropertiesModel(
                        index = event.index,
                        workoutTypeEnum = workoutType,
                        editWorkoutFunction = EditWorkoutListFunctions.DELETE_WORKOUT_ITEM
                    )
                    if (newPropertiesModel == null) {
                        viewModelScope.launch {
                            event.onDeleteErrorCallback()
                        }
                        return
                    }
                    workoutModel.workoutDetailsModel.workoutPropertiesModel =
                        newPropertiesModel
                    val realmEntry =
                        workoutModel.toRealmWorkoutEntry(workoutType = event.workoutType)
                    viewModelScope.launch {
                        val isUpdateSuccess = realmWorkoutEntryUseCase
                            .updateSingleWorkoutEntryToRealmDbUseCase(
                                updatedRealmWorkoutEntry = realmEntry
                            )
                        if (isUpdateSuccess) {
                            updateWorkoutState(
                                newEditWorkoutUiState = editWorkoutState.copy(
                                    weightLiftingPropertyList = newPropertiesModel.getWeightLiftingProps(),
                                    cardioPropertyList = newPropertiesModel.getCardioProps(),
                                    calisthenicsPropertyList = newPropertiesModel.getCalisthenicsProps(),
                                    reps = "",
                                    sets = "",
                                    weight = ""
                                )
                            )
                        } else {
                            event.onDeleteErrorCallback()
                        }
                    }
                } catch (e: Exception) {
                    viewModelScope.launch {
                        event.onDeleteErrorCallback()
                    }
                }
            }

            is EditWorkoutEvents.UpdateWorkoutListItem -> TODO()
            is EditWorkoutEvents.DeleteEntireWorkout -> {
                try {
                    viewModelScope.launch {
                        val wasDeleteSuccessful =
                            realmWorkoutEntryUseCase.deleteWorkoutEntryFromRealmDbUseCase(workoutId = event.workoutId)
                        if (wasDeleteSuccessful) {
                            event.onSuccessfulDeleteCallback()
                        } else {
                            event.onDeleteErrorCallback()
                        }
                    }
                } catch (e: Exception) {
                    viewModelScope.launch {
                        event.onDeleteErrorCallback()
                    }
                }
            }

            is EditWorkoutEvents.EditTime -> {
                updateTimeValue(
                    value = event.value,
                    timeDeterminate = event.timeDeterminate
                )
            }

            is EditWorkoutEvents.AddNewCalisthenicSetToWorkout -> {
                val adjustedTimeValues =
                    editWorkoutUseCase.adjustTimeValuesUseCase(event.newCalisthenicItem.time)
                event.newCalisthenicItem.time = adjustedTimeValues
                val validWorkout = isCalisthenicsPropertiesValid(event.newCalisthenicItem)
                if (validWorkout.isWorkoutValid()) {
                    val newPropsModel = getNewWorkoutPropertiesModel(
                        workoutTypeEnum = WorkoutTypeEnum.CALISTHENICS,
                        editWorkoutFunction = EditWorkoutListFunctions.ADD_WORKOUT_ITEM,
                        newCalisthenicsItem = event.newCalisthenicItem
                    )
                    if (newPropsModel == null) {
                        viewModelScope.launch {
                            event.onAddErrorCallback()
                        }
                        return
                    }
                    val workoutModel = event.workoutModel
                    workoutModel.workoutDetailsModel.workoutPropertiesModel = newPropsModel
                    try {
                        val updatedRealmEntry =
                            workoutModel.toRealmWorkoutEntry(
                                workoutType = event.workoutType
                            )
                        viewModelScope.launch {
                            val isUpdateSuccess = realmWorkoutEntryUseCase
                                .updateSingleWorkoutEntryToRealmDbUseCase(
                                    updatedRealmWorkoutEntry = updatedRealmEntry
                                )
                            if (isUpdateSuccess) {
                                updateWorkoutState(
                                    newEditWorkoutUiState = editWorkoutState.copy(
                                        calisthenicsPropertyList = newPropsModel.getCalisthenicsProps(),
                                        reps = "",
                                        sets = "",
                                        weight = "",
                                        hour = "",
                                        minute = "",
                                        second = ""
                                    )
                                )
                            } else {
                                event.onAddErrorCallback()
                            }
                        }
                    } catch (e: Exception) {
                        viewModelScope.launch {
                            event.onAddErrorCallback()
                        }
                    }
                }
            }

            is EditWorkoutEvents.AddNewCardioSetToWorkout -> {
                val adjustedTimeValues =
                    editWorkoutUseCase.adjustMandatoryTimeValuesUseCase(event.newCardioItem.time)
                event.newCardioItem.time = adjustedTimeValues
                val validWorkout = isCardioPropertiesValid(event.newCardioItem)
                if (validWorkout.isWorkoutValid()) {
                    val newPropsModel = getNewWorkoutPropertiesModel(
                        workoutTypeEnum = WorkoutTypeEnum.CARDIO,
                        editWorkoutFunction = EditWorkoutListFunctions.ADD_WORKOUT_ITEM,
                        newCardioItem = event.newCardioItem
                    )
                    if (newPropsModel == null) {
                        viewModelScope.launch {
                            event.onAddErrorCallback()
                        }
                        return
                    }
                    val workoutModel = event.workoutModel
                    workoutModel.workoutDetailsModel.workoutPropertiesModel = newPropsModel
                    try {
                        val updatedRealmEntry =
                            workoutModel.toRealmWorkoutEntry(
                                workoutType = event.workoutType
                            )
                        viewModelScope.launch {
                            val isUpdateSuccess = realmWorkoutEntryUseCase
                                .updateSingleWorkoutEntryToRealmDbUseCase(
                                    updatedRealmWorkoutEntry = updatedRealmEntry
                                )
                            if (isUpdateSuccess) {
                                updateWorkoutState(
                                    newEditWorkoutUiState = editWorkoutState.copy(
                                        cardioPropertyList = newPropsModel.getCardioProps(),
                                        laps = "",
                                        distance = "",
                                        hour = "",
                                        minute = "",
                                        second = ""
                                    )
                                )
                            } else {
                                event.onAddErrorCallback()
                            }
                        }
                    } catch (e: Exception) {
                        viewModelScope.launch {
                            event.onAddErrorCallback()
                        }
                    }
                }
            }

            is EditWorkoutEvents.EditLaps -> {
                addOrSubtractLaps(
                    editWorkoutFunction = event.editWorkoutFunction,
                    lapValue = event.value
                )
            }

            is EditWorkoutEvents.OnLapsValueChange -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        laps = event.lapValue,
                        isLapsErrorVisible = false
                    )
                )
            }
            is EditWorkoutEvents.EditDistance -> {
                addOrSubtractDistance(
                    editWorkoutFunction = event.editWorkoutFunction,
                    distanceValue = event.value
                )
            }
            is EditWorkoutEvents.OnDistanceValueChange -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        distance = event.distanceValue,
                        isDistanceErrorVisible = false
                    )
                )
            }
            EditWorkoutEvents.EditDistanceType -> {
                updateWorkoutState(
                    editWorkoutState.copy(
                        distanceType = editWorkoutState.distanceType.getNextDistanceType()
                    )
                )
            }
        }
    }

    private fun isCalisthenicsPropertiesValid(
        workoutProperties: CalisthenicsModel
    ): CalisthenicsValidator {
        val workoutTime = workoutProperties.time
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutState.sets)
        val isTimeValid =
            if (workoutTime != null) editWorkoutUseCase.isTimeValidUseCase(workoutTime) else true
        val isWeightValid =
            if (workoutProperties.weight != null) {
                editWorkoutUseCase.isDoubleValidUseCase(
                    editWorkoutState.weight
                )
            } else {
                true
            }
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                isRepsErrorVisible = !isRepsValid,
                isSetsErrorVisible = !isSetsValid,
                isWeightErrorVisible = !isWeightValid,
                isTimeErrorVisible = !isTimeValid
            )
        )
        return CalisthenicsValidator(
            isRepsValid = isRepsValid,
            isSetsValid = isSetsValid,
            isWeightValid = isWeightValid,
            isTimeValid = isTimeValid
        )
    }

    private fun isCardioPropertiesValid(
        workoutProperties: CardioModel
    ): CardioValidator {
        val workoutTime = workoutProperties.time
        val isLapsValid = if (editWorkoutState.laps != "") {
            editWorkoutUseCase.isDoubleValidUseCase(editWorkoutState.laps)
        } else {
            true
        }
        val isDistanceValid = editWorkoutUseCase.isDoubleValidUseCase(editWorkoutState.distance)
        val isTimeValid = editWorkoutUseCase.isTimeValidUseCase(workoutTime)
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                isLapsErrorVisible = !isLapsValid,
                isDistanceErrorVisible = !isDistanceValid,
                isTimeErrorVisible = !isTimeValid
            )
        )
        return CardioValidator(
            isLapsValid = isLapsValid,
            isDistanceValid = isDistanceValid,
            isTimeValid = isTimeValid
        )
    }

    private fun getNewWorkoutPropertiesModel(
        index: Int = (-1),
        workoutTypeEnum: WorkoutTypeEnum,
        editWorkoutFunction: EditWorkoutListFunctions,
        newWeightLiftingItem: WeightLiftingModel? = null,
        newCardioItem: CardioModel? = null,
        newCalisthenicsItem: CalisthenicsModel? = null
    ): WorkoutPropertiesModel? {
        return when (workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                when (editWorkoutFunction) {
                    EditWorkoutListFunctions.ADD_WORKOUT_ITEM -> {
                        if (newWeightLiftingItem != null) {
                            val weightTrainingWorkoutList =
                                editWorkoutState.weightLiftingPropertyList
                            weightTrainingWorkoutList.add(newWeightLiftingItem)
                            WorkoutPropertiesModel
                                .WeightLiftingProps(weightTrainingWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val weightTrainingWorkoutList = editWorkoutState.weightLiftingPropertyList
                        weightTrainingWorkoutList.removeAt(index)
                        WorkoutPropertiesModel
                            .WeightLiftingProps(weightTrainingWorkoutList)
                    }
                }
            }

            WorkoutTypeEnum.CALISTHENICS -> {
                when (editWorkoutFunction) {
                    EditWorkoutListFunctions.ADD_WORKOUT_ITEM -> {
                        if (newCalisthenicsItem != null) {
                            val calisthenicsWorkoutList =
                                editWorkoutState.calisthenicsPropertyList
                            calisthenicsWorkoutList.add(newCalisthenicsItem)
                            WorkoutPropertiesModel
                                .CalisthenicsProps(calisthenicsWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val calisthenicsWorkoutList = editWorkoutState.calisthenicsPropertyList
                        calisthenicsWorkoutList.removeAt(index)
                        WorkoutPropertiesModel
                            .CalisthenicsProps(calisthenicsWorkoutList)
                    }
                }
            }

            WorkoutTypeEnum.CARDIO -> {
                when (editWorkoutFunction) {
                    EditWorkoutListFunctions.ADD_WORKOUT_ITEM -> {
                        if (newCardioItem != null) {
                            val cardioWorkoutList =
                                editWorkoutState.cardioPropertyList
                            cardioWorkoutList.add(newCardioItem)
                            WorkoutPropertiesModel
                                .CardioProps(cardioWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val cardioWorkoutList = editWorkoutState.cardioPropertyList
                        cardioWorkoutList.removeAt(index)
                        WorkoutPropertiesModel
                            .CardioProps(cardioWorkoutList)
                    }
                }
            }
        }
    }

    private fun areWeightLiftingPropertiesValid(): WeightLiftingValidator {
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutState.sets)
        val isWeightValid = editWorkoutUseCase.isDoubleValidUseCase(editWorkoutState.weight)
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                isRepsErrorVisible = !isRepsValid,
                isSetsErrorVisible = !isSetsValid,
                isWeightErrorVisible = !isWeightValid
            )
        )
        return WeightLiftingValidator(
            isRepsValid = isRepsValid,
            isSetsValid = isSetsValid,
            isWeightValid = isWeightValid
        )
    }

    fun getSingleWorkout(
        workoutId: String?
    ) {
        updateWorkoutState(editWorkoutState.copy(workout = UiState.Loading))
        if (workoutId.isNullOrEmpty()) {
            updateWorkoutState(editWorkoutState.copy(workout = UiState.Error))
            return
        }
        try {
            viewModelScope.launch {
                val workout = realmWorkoutEntryUseCase.getSingleRealmWorkoutEntry(workoutId)
                val workoutLists: Triple<SnapshotStateList<WeightLiftingModel>, SnapshotStateList<CardioModel>, SnapshotStateList<CalisthenicsModel>> =
                    when (workout) {
                        is UiState.Success -> {
                            when (
                                val workoutList =
                                    workout.data.workoutDetailsModel.workoutPropertiesModel
                            ) {
                                is WorkoutPropertiesModel.CalisthenicsProps -> {
                                    val calisthenicsProps = workoutList.props.toMutableStateList()
                                    calisthenicsProps.forEach {
                                        it.time =
                                            editWorkoutUseCase.adjustTimeValuesUseCase(it.time)
                                    }
                                    Triple(
                                        mutableStateListOf(),
                                        mutableStateListOf(),
                                        calisthenicsProps
                                    )
                                }

                                is WorkoutPropertiesModel.CardioProps -> {
                                    Triple(
                                        mutableStateListOf(),
                                        workoutList.props.toMutableStateList(),
                                        mutableStateListOf()
                                    )
                                }

                                is WorkoutPropertiesModel.WeightLiftingProps -> {
                                    Triple(
                                        workoutList.props.toMutableStateList(),
                                        mutableStateListOf(),
                                        mutableStateListOf()
                                    )
                                }
                            }
                        }

                        else -> Triple(
                            mutableStateListOf(),
                            mutableStateListOf(),
                            mutableStateListOf()
                        )
                    }
                updateWorkoutState(
                    editWorkoutState.copy(
                        workout = workout,
                        weightLiftingPropertyList = workoutLists.first,
                        cardioPropertyList = workoutLists.second,
                        calisthenicsPropertyList = workoutLists.third
                    )
                )
            }
        } catch (e: IllegalStateException) {
        } catch (e: Exception) {
        }
    }

    private fun updateWorkoutState(newEditWorkoutUiState: EditWorkoutUiState) {
        editWorkoutState = newEditWorkoutUiState
    }

    private fun addOrSubtractReps(
        editWorkoutFunction: EditWorkoutFunction,
        repValue: String
    ) {
        val newRepValue = editWorkoutUseCase.addOrSubtractIntegersUseCase(
            editWorkoutFunction = editWorkoutFunction,
            value = repValue
        )
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                reps = newRepValue,
                isRepsErrorVisible = false
            )
        )
    }

    private fun addOrSubtractSets(
        editWorkoutFunction: EditWorkoutFunction,
        setValue: String
    ) {
        val newSetValue = editWorkoutUseCase.addOrSubtractIntegersUseCase(
            editWorkoutFunction = editWorkoutFunction,
            value = setValue
        )
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                sets = newSetValue,
                isSetsErrorVisible = false
            )
        )
    }

    private fun addOrSubtractWeight(
        editWorkoutFunction: EditWorkoutFunction,
        weightValue: String,
        valueDifferential: Double
    ) {
        val newWeightValue = editWorkoutUseCase.addOrSubtractDoublesUseCase(
            editWorkoutFunction = editWorkoutFunction,
            value = weightValue,
            valueDifferential = valueDifferential
        )
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                weight = newWeightValue,
                isWeightErrorVisible = false
            )
        )
    }

    private fun addOrSubtractLaps(
        editWorkoutFunction: EditWorkoutFunction,
        lapValue: String
    ) {
        val newLapValue = editWorkoutUseCase.addOrSubtractDoublesUseCase(
            editWorkoutFunction = editWorkoutFunction,
            value = lapValue
        )
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                laps = newLapValue,
                isLapsErrorVisible = false
            )
        )
    }

    private fun addOrSubtractDistance(
        editWorkoutFunction: EditWorkoutFunction,
        distanceValue: String
    ) {
        val newDistanceValue = editWorkoutUseCase.addOrSubtractDoublesUseCase(
            editWorkoutFunction = editWorkoutFunction,
            value = distanceValue
        )
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutState.copy(
                distance = newDistanceValue,
                isDistanceErrorVisible = false
            )
        )
    }

    private fun clearWorkoutFields(workoutTypeEnum: WorkoutTypeEnum) {
        when (workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        reps = "",
                        sets = "",
                        weight = "",
                        isRepsErrorVisible = false,
                        isSetsErrorVisible = false,
                        isWeightErrorVisible = false
                    )
                )
            }

            WorkoutTypeEnum.CALISTHENICS -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        reps = "",
                        sets = "",
                        weight = "",
                        hour = "",
                        minute = "",
                        second = "",
                        isRepsErrorVisible = false,
                        isSetsErrorVisible = false,
                        isWeightErrorVisible = false,
                        isTimeErrorVisible = false
                    )
                )
            }

            WorkoutTypeEnum.CARDIO -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        laps = "",
                        distance = "",
                        hour = "",
                        minute = "",
                        second = "",
                        isLapsErrorVisible = false,
                        isTimeErrorVisible = false,
                        isDistanceErrorVisible = false
                    )
                )
            }
        }
    }

    private fun updateTimeValue(
        value: String,
        timeDeterminate: EditWorkoutTimeDeterminate
    ) {
        when (timeDeterminate) {
            EditWorkoutTimeDeterminate.HOUR -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        hour = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.MINUTE -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        minute = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.SECOND -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        second = value
                    )
                )
            }
        }
    }
}
