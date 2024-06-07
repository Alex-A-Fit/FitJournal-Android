package com.example.fitjournal.home.presentation.screen.editworkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.usecase.editworkoutdialog.CreateModelForEditWorkoutDialogUseCase
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.domain.usecase.workout.EditWorkoutUseCase
import com.example.fitjournal.core.domain.util.HelperFunctions
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.toCalisthenicsModel
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.toCardioModel
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.toWeightLiftingModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.mapper.toRealmWorkoutEntry
import com.example.fitjournal.home.presentation.model.enum.EditWorkoutListFunctions
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState
import com.example.fitjournal.home.presentation.model.ui.CalisthenicsValidator
import com.example.fitjournal.home.presentation.model.ui.CardioValidator
import com.example.fitjournal.home.presentation.model.ui.WeightLiftingValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class EditWorkoutViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase,
    private val editWorkoutUseCase: EditWorkoutUseCase,
    private val createModelForEditWorkoutDialogUseCase: CreateModelForEditWorkoutDialogUseCase
) : ViewModel() {
    var editWorkoutUiState: EditWorkoutUiState by mutableStateOf(
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
                    newEditWorkoutUiState = editWorkoutUiState.copy(
                        reps = event.repValue,
                        isRepsErrorVisible = false
                    )
                )
            }

            is EditWorkoutEvents.OnSetValueChange -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutUiState.copy(
                        sets = event.setValue,
                        isSetsErrorVisible = false
                    )
                )
            }

            is EditWorkoutEvents.OnWeightValueChange -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutUiState.copy(
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
                        callUpdateWorkoutUseCase(
                            updatedRealmEntry = updatedRealmEntry,
                            newPropsModel = newPropsModel,
                            onErrorCallback = event.onAddErrorCallback,
                            clearFields = true
                        )
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
                                newEditWorkoutUiState = editWorkoutUiState.copy(
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

            is EditWorkoutEvents.UpdateWorkoutListItem -> {
                val workout = event.workoutModel
                when (event.workoutTypeDialog) {
                    is WorkoutTypeDialog.Calisthenics -> {
                        val updatedWorkoutSet =
                            event.workoutTypeDialog.editWorkoutSetCalisthenicsModel
                        val workoutListOfSets = editWorkoutUiState.calisthenicsPropertyList
                        workoutListOfSets[updatedWorkoutSet.index] =
                            updatedWorkoutSet.toCalisthenicsModel()
                        workout.workoutDetailsModel.workoutPropertiesModel =
                            WorkoutPropertiesModel.CalisthenicsProps(workoutListOfSets)
                        try {
                            val updatedRealmEntry =
                                workout.toRealmWorkoutEntry(
                                    workoutType = event.workoutType
                                )
                            callUpdateWorkoutUseCase(
                                updatedRealmEntry = updatedRealmEntry,
                                newPropsModel = workout.workoutDetailsModel.workoutPropertiesModel,
                                onErrorCallback = event.onUpdateErrorCallback,
                                onSuccessCallback = event.onSuccessfulUpdateCallback,
                                clearFields = false
                            )
                        } catch (e: Exception) {
                            viewModelScope.launch {
                                event.onUpdateErrorCallback()
                            }
                        }
                    }

                    is WorkoutTypeDialog.Cardio -> {
                        val updatedWorkoutSet =
                            event.workoutTypeDialog.editWorkoutSetCardioModel
                        val workoutListOfSets = editWorkoutUiState.cardioPropertyList
                        workoutListOfSets[updatedWorkoutSet.index] =
                            updatedWorkoutSet.toCardioModel()
                        workout.workoutDetailsModel.workoutPropertiesModel =
                            WorkoutPropertiesModel.CardioProps(workoutListOfSets)
                        try {
                            val updatedRealmEntry =
                                workout.toRealmWorkoutEntry(
                                    workoutType = event.workoutType
                                )
                            callUpdateWorkoutUseCase(
                                updatedRealmEntry = updatedRealmEntry,
                                newPropsModel = workout.workoutDetailsModel.workoutPropertiesModel,
                                onErrorCallback = event.onUpdateErrorCallback,
                                onSuccessCallback = event.onSuccessfulUpdateCallback,
                                clearFields = false
                            )
                        } catch (e: Exception) {
                            viewModelScope.launch {
                                event.onUpdateErrorCallback()
                            }
                        }
                    }

                    is WorkoutTypeDialog.WeightLifting -> {
                        val updatedWorkoutSet =
                            event.workoutTypeDialog.editWorkoutSetWeightLiftingModel
                        val workoutListOfSets = editWorkoutUiState.weightLiftingPropertyList
                        workoutListOfSets[updatedWorkoutSet.index] =
                            updatedWorkoutSet.toWeightLiftingModel()
                        workout.workoutDetailsModel.workoutPropertiesModel =
                            WorkoutPropertiesModel.WeightLiftingProps(workoutListOfSets)
                        try {
                            val updatedRealmEntry =
                                workout.toRealmWorkoutEntry(
                                    workoutType = event.workoutType
                                )
                            callUpdateWorkoutUseCase(
                                updatedRealmEntry = updatedRealmEntry,
                                newPropsModel = workout.workoutDetailsModel.workoutPropertiesModel,
                                onErrorCallback = event.onUpdateErrorCallback,
                                clearFields = false,
                                onSuccessCallback = event.onSuccessfulUpdateCallback
                            )
                        } catch (e: Exception) {
                            viewModelScope.launch {
                                event.onUpdateErrorCallback()
                            }
                        }
                    }

                    WorkoutTypeDialog.None -> Unit
                }
            }

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
                        callUpdateWorkoutUseCase(
                            updatedRealmEntry = updatedRealmEntry,
                            newPropsModel = newPropsModel,
                            onErrorCallback = event.onAddErrorCallback,
                            clearFields = true
                        )
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
                        callUpdateWorkoutUseCase(
                            updatedRealmEntry = updatedRealmEntry,
                            newPropsModel = newPropsModel,
                            onErrorCallback = event.onAddErrorCallback,
                            clearFields = true
                        )
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
                    newEditWorkoutUiState = editWorkoutUiState.copy(
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
                    newEditWorkoutUiState = editWorkoutUiState.copy(
                        distance = event.distanceValue,
                        isDistanceErrorVisible = false
                    )
                )
            }

            EditWorkoutEvents.EditDistanceType -> {
                updateWorkoutState(
                    editWorkoutUiState.copy(
                        distanceType = editWorkoutUiState.distanceType.getOtherDistanceType()
                    )
                )
            }

            EditWorkoutEvents.EditWeightType -> {
                updateWorkoutState(
                    editWorkoutUiState.copy(
                        weightType = editWorkoutUiState.weightType.getOtherWeightType()
                    )
                )
            }

            is EditWorkoutEvents.GetWorkoutSet -> {
                val workoutSet = createModelForEditWorkoutDialogUseCase(
                    workoutTypeEnum = event.workoutTypeEnum,
                    workoutPropertiesModel = event.workoutPropertiesModel,
                    index = event.index
                )
                event.getWorkoutSetCallback(workoutSet)
            }

            is EditWorkoutEvents.SelectDateFromDatePicker -> {
                val selectedDate = DateManager.getSelectedDate(
                    event.userSelectedDate
                )
                try {
                    val workout = event.workout.copy(date = selectedDate.localDateString)
                    viewModelScope.launch {
                        val wasUpdateSuccess =
                            realmWorkoutEntryUseCase.updateSingleWorkoutEntryToRealmDbUseCase(
                                updatedRealmWorkoutEntry = workout.toRealmWorkoutEntry(event.workoutType)
                            )
                        if (wasUpdateSuccess) {
                            updateWorkoutState(
                                newEditWorkoutUiState = editWorkoutUiState.copy(
                                    localDate = selectedDate.localDateString,
                                    localDateInMillis = (
                                        selectedDate.localDateTime.toEpochSecond(
                                            ZoneOffset.UTC
                                        ) * 1000
                                        )
                                )
                            )
                            event.onSuccessfulUpdateCallback()
                        } else {
                            event.onErrorCallback()
                        }
                    }
                } catch (e: Exception) {
                    viewModelScope.launch {
                        event.onErrorCallback()
                    }
                }
            }

            is EditWorkoutEvents.UpdateDate -> {
                val date = HelperFunctions.parseDate(event.date)
                updateWorkoutState(
                    newEditWorkoutUiState =
                    editWorkoutUiState.copy(
                        localDate = date.format(DateManager.getCommonDateFormat()),
                        localDateInMillis = (date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                    )
                )
            }
        }
    }

    private fun isCalisthenicsPropertiesValid(
        workoutProperties: CalisthenicsModel
    ): CalisthenicsValidator {
        val workoutTime = workoutProperties.time
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutUiState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutUiState.sets)
        val isTimeValid =
            if (workoutTime != null) editWorkoutUseCase.isTimeValidUseCase(workoutTime) else true
        val isWeightValid =
            if (workoutProperties.weight != null) {
                editWorkoutUseCase.isDoubleValidUseCase(
                    editWorkoutUiState.weight
                )
            } else {
                true
            }
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutUiState.copy(
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
        val isLapsValid = if (editWorkoutUiState.laps != "") {
            editWorkoutUseCase.isDoubleValidUseCase(editWorkoutUiState.laps)
        } else {
            true
        }
        val isDistanceValid = editWorkoutUseCase.isDoubleValidUseCase(editWorkoutUiState.distance)
        val isTimeValid = editWorkoutUseCase.isTimeValidUseCase(workoutTime)
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutUiState.copy(
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
                                editWorkoutUiState.weightLiftingPropertyList
                            weightTrainingWorkoutList.add(newWeightLiftingItem)
                            WorkoutPropertiesModel
                                .WeightLiftingProps(weightTrainingWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val weightTrainingWorkoutList = editWorkoutUiState.weightLiftingPropertyList
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
                                editWorkoutUiState.calisthenicsPropertyList
                            calisthenicsWorkoutList.add(newCalisthenicsItem)
                            WorkoutPropertiesModel
                                .CalisthenicsProps(calisthenicsWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val calisthenicsWorkoutList = editWorkoutUiState.calisthenicsPropertyList
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
                                editWorkoutUiState.cardioPropertyList
                            cardioWorkoutList.add(newCardioItem)
                            WorkoutPropertiesModel
                                .CardioProps(cardioWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val cardioWorkoutList = editWorkoutUiState.cardioPropertyList
                        cardioWorkoutList.removeAt(index)
                        WorkoutPropertiesModel
                            .CardioProps(cardioWorkoutList)
                    }
                }
            }
        }
    }

    private fun areWeightLiftingPropertiesValid(): WeightLiftingValidator {
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutUiState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(editWorkoutUiState.sets)
        val isWeightValid = editWorkoutUseCase.isDoubleValidUseCase(editWorkoutUiState.weight)
        updateWorkoutState(
            newEditWorkoutUiState = editWorkoutUiState.copy(
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
        updateWorkoutState(editWorkoutUiState.copy(workout = UiState.Loading))
        if (workoutId.isNullOrEmpty()) {
            updateWorkoutState(editWorkoutUiState.copy(workout = UiState.Error))
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
                    editWorkoutUiState.copy(
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
        editWorkoutUiState = newEditWorkoutUiState
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
            newEditWorkoutUiState = editWorkoutUiState.copy(
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
            newEditWorkoutUiState = editWorkoutUiState.copy(
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
            newEditWorkoutUiState = editWorkoutUiState.copy(
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
            newEditWorkoutUiState = editWorkoutUiState.copy(
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
            newEditWorkoutUiState = editWorkoutUiState.copy(
                distance = newDistanceValue,
                isDistanceErrorVisible = false
            )
        )
    }

    private fun clearWorkoutFields(workoutTypeEnum: WorkoutTypeEnum) {
        when (workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutUiState.copy(
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
                    newEditWorkoutUiState = editWorkoutUiState.copy(
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
                    newEditWorkoutUiState = editWorkoutUiState.copy(
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
                    newEditWorkoutUiState = editWorkoutUiState.copy(
                        hour = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.MINUTE -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutUiState.copy(
                        minute = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.SECOND -> {
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutUiState.copy(
                        second = value
                    )
                )
            }
        }
    }

    private fun callUpdateWorkoutUseCase(
        updatedRealmEntry: RealmWorkoutEntry,
        newPropsModel: WorkoutPropertiesModel,
        onErrorCallback: suspend () -> Unit,
        onSuccessCallback: () -> Unit = {},
        clearFields: Boolean
    ) {
        viewModelScope.launch {
            val isUpdateSuccess = realmWorkoutEntryUseCase
                .updateSingleWorkoutEntryToRealmDbUseCase(
                    updatedRealmWorkoutEntry = updatedRealmEntry
                )
            if (isUpdateSuccess) {
                if (clearFields) {
                    updateWorkoutState(
                        newEditWorkoutUiState = editWorkoutUiState.copy(
                            weightLiftingPropertyList = newPropsModel.getWeightLiftingProps(),
                            cardioPropertyList = newPropsModel.getCardioProps(),
                            calisthenicsPropertyList = newPropsModel.getCalisthenicsProps(),
                            reps = "",
                            sets = "",
                            weight = "",
                            hour = "",
                            minute = "",
                            second = "",
                            laps = "",
                            distance = ""
                        )
                    )
                } else {
                    updateWorkoutState(
                        newEditWorkoutUiState = editWorkoutUiState.copy(
                            weightLiftingPropertyList = newPropsModel.getWeightLiftingProps(),
                            cardioPropertyList = newPropsModel.getCardioProps(),
                            calisthenicsPropertyList = newPropsModel.getCalisthenicsProps()
                        )
                    )
                }
                onSuccessCallback()
            } else {
                onErrorCallback()
            }
        }
    }
}
