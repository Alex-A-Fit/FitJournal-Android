package com.example.fitjournal.journalEntry.screen.journalEntry.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.R
import com.example.fitjournal.core.data.model.results.Result
import com.example.fitjournal.core.data.util.getWorkoutType
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutDetailsModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.domain.usecase.workout.EditWorkoutUseCase
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.localdate.formatToCommonDate
import com.example.fitjournal.home.presentation.model.enum.EditWorkoutListFunctions
import com.example.fitjournal.home.presentation.model.ui.CalisthenicsValidator
import com.example.fitjournal.home.presentation.model.ui.CardioValidator
import com.example.fitjournal.home.presentation.model.ui.WeightLiftingValidator
import com.example.fitjournal.journalEntry.model.AddWorkoutDetailUiState
import com.example.fitjournal.journalEntry.model.events.AddWorkoutDetailEvents
import com.example.fitjournal.statistics.domain.mapper.toRealmWorkoutEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddWorkoutDetailViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase,
    private val editWorkoutUseCase: EditWorkoutUseCase
) : ViewModel() {
    var addWorkoutDetailUiState: AddWorkoutDetailUiState by mutableStateOf(
        AddWorkoutDetailUiState(
            addWorkoutDetailEvents = ::journalEntryDetailsEvents
        )
    )
        private set

    private var addWorkoutJob: Job? = null

    private fun journalEntryDetailsEvents(event: AddWorkoutDetailEvents) {
        when (event) {
            is AddWorkoutDetailEvents.AddNewCalisthenicSetToWorkout -> {
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
                    updateWorkoutState(
                        newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                            calisthenicsPropertyList = newPropsModel?.getCalisthenicsProps()
                                ?: mutableStateListOf(),
                            reps = "",
                            sets = "",
                            weight = "",
                            hour = "",
                            minute = "",
                            second = ""
                        )
                    )
                }
            }

            is AddWorkoutDetailEvents.AddNewCardioSetToWorkout -> {
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
                    updateWorkoutState(
                        newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                            cardioPropertyList = newPropsModel?.getCardioProps()
                                ?: mutableStateListOf(),
                            laps = "",
                            distance = "",
                            hour = "",
                            minute = "",
                            second = ""
                        )
                    )
                }
            }

            is AddWorkoutDetailEvents.AddNewWeightTrainingSetToWorkout -> {
                val workoutValidity = areWeightLiftingPropertiesValid()
                if (workoutValidity.isWorkoutValid()) {
                    val newPropsModel = getNewWorkoutPropertiesModel(
                        workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
                        editWorkoutFunction = EditWorkoutListFunctions.ADD_WORKOUT_ITEM,
                        newWeightLiftingItem = event.newWeightLiftingItem
                    )
                    updateWorkoutState(
                        newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                            weightLiftingPropertyList = newPropsModel?.getWeightLiftingProps()
                                ?: mutableStateListOf(),
                            reps = "",
                            sets = "",
                            weight = ""
                        )
                    )
                }
            }

            is AddWorkoutDetailEvents.ClearWorkoutTextFields -> {
                clearWorkoutFields(event.workoutTypeEnum)
            }

            is AddWorkoutDetailEvents.DeleteWorkoutSetItemInWorkoutModelList -> {
                val workoutType = addWorkoutDetailUiState.workoutTypeEnum
                if (workoutType == null) {
                    viewModelScope.launch {
                        event.onDeleteErrorCallback()
                    }
                    return
                }
                try {
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
                    updateWorkoutState(
                        newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                            weightLiftingPropertyList = newPropertiesModel.getWeightLiftingProps(),
                            cardioPropertyList = newPropertiesModel.getCardioProps(),
                            calisthenicsPropertyList = newPropertiesModel.getCalisthenicsProps(),
                            reps = "",
                            sets = "",
                            weight = ""
                        )
                    )
                } catch (e: Exception) {
                    viewModelScope.launch {
                        event.onDeleteErrorCallback()
                    }
                }
            }

            is AddWorkoutDetailEvents.EditDistance -> {
                addOrSubtractDistance(
                    editWorkoutFunction = event.editWorkoutFunction,
                    distanceValue = event.value
                )
            }

            AddWorkoutDetailEvents.EditDistanceType -> {
                val newDistanceType = addWorkoutDetailUiState.distanceType.getOtherDistanceType()
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        distanceType = newDistanceType
                    )
                )
            }

            is AddWorkoutDetailEvents.EditLaps -> {
                addOrSubtractLaps(
                    editWorkoutFunction = event.editWorkoutFunction,
                    lapValue = event.value
                )
            }

            is AddWorkoutDetailEvents.EditReps -> {
                addOrSubtractReps(
                    editWorkoutFunction = event.editWorkoutFunction,
                    repValue = event.repValue
                )
            }

            is AddWorkoutDetailEvents.EditSets -> {
                addOrSubtractSets(
                    editWorkoutFunction = event.editWorkoutFunction,
                    setValue = event.setValue
                )
            }

            is AddWorkoutDetailEvents.EditTime -> {
                updateTimeValue(
                    value = event.value,
                    timeDeterminate = event.timeDeterminate
                )
            }

            is AddWorkoutDetailEvents.EditWeight -> {
                addOrSubtractWeight(
                    editWorkoutFunction = event.editWorkoutFunction,
                    weightValue = event.weightValue,
                    valueDifferential = event.valueDifferential
                )
            }

            AddWorkoutDetailEvents.EditWeightType -> {
                val getOtherWeightType = addWorkoutDetailUiState.weightType.getOtherWeightType()
                updateWorkoutState(
                    addWorkoutDetailUiState.copy(
                        weightType = getOtherWeightType
                    )
                )
            }

            is AddWorkoutDetailEvents.OnDistanceValueChange -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        distance = event.distanceValue,
                        isDistanceErrorVisible = false
                    )
                )
            }

            is AddWorkoutDetailEvents.OnLapsValueChange -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        laps = event.lapValue,
                        isLapsErrorVisible = false
                    )
                )
            }

            is AddWorkoutDetailEvents.OnRepValueChange -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        reps = event.repValue,
                        isRepsErrorVisible = false
                    )
                )
            }

            is AddWorkoutDetailEvents.OnSetValueChange -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        sets = event.setValue,
                        isSetsErrorVisible = false
                    )
                )
            }

            is AddWorkoutDetailEvents.OnWeightValueChange -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        weight = event.weightValue,
                        isWeightErrorVisible = false
                    )
                )
            }

            is AddWorkoutDetailEvents.UpdateWorkoutListItem -> TODO()
            AddWorkoutDetailEvents.ClearViewModelState -> {
                addWorkoutDetailUiState = AddWorkoutDetailUiState(
                    workoutName = addWorkoutDetailUiState.workoutName,
                    workoutType = addWorkoutDetailUiState.workoutType,
                    workoutTypeEnum = addWorkoutDetailUiState.workoutTypeEnum,
                    addWorkoutDetailEvents = ::journalEntryDetailsEvents
                )
            }

            is AddWorkoutDetailEvents.AddWorkoutToRealm -> {
                addWorkoutJob = viewModelScope.launch {
                    addWorkoutToRealm { realmResult ->
                        event.callback(realmResult)
                    }
                }
                addWorkoutJob?.start()
            }

            AddWorkoutDetailEvents.StopAddWorkoutJob -> {
                if (addWorkoutJob != null) {
                    addWorkoutJob?.cancel("User clicked back button")
                }
            }
        }
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
                                addWorkoutDetailUiState.weightLiftingPropertyList
                            weightTrainingWorkoutList.add(newWeightLiftingItem)
                            WorkoutPropertiesModel
                                .WeightLiftingProps(weightTrainingWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val weightTrainingWorkoutList =
                            addWorkoutDetailUiState.weightLiftingPropertyList
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
                                addWorkoutDetailUiState.calisthenicsPropertyList
                            calisthenicsWorkoutList.add(newCalisthenicsItem)
                            WorkoutPropertiesModel
                                .CalisthenicsProps(calisthenicsWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val calisthenicsWorkoutList =
                            addWorkoutDetailUiState.calisthenicsPropertyList
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
                                addWorkoutDetailUiState.cardioPropertyList
                            cardioWorkoutList.add(newCardioItem)
                            WorkoutPropertiesModel
                                .CardioProps(cardioWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val cardioWorkoutList = addWorkoutDetailUiState.cardioPropertyList
                        cardioWorkoutList.removeAt(index)
                        WorkoutPropertiesModel
                            .CardioProps(cardioWorkoutList)
                    }
                }
            }
        }
    }

    private fun isCardioPropertiesValid(
        workoutProperties: CardioModel
    ): CardioValidator {
        val workoutTime = workoutProperties.time
        val isLapsValid = if (addWorkoutDetailUiState.laps != "") {
            editWorkoutUseCase.isDoubleValidUseCase(addWorkoutDetailUiState.laps)
        } else {
            true
        }
        val isDistanceValid =
            editWorkoutUseCase.isDoubleValidUseCase(addWorkoutDetailUiState.distance)
        val isTimeValid = editWorkoutUseCase.isTimeValidUseCase(workoutTime)
        updateWorkoutState(
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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

    private fun isCalisthenicsPropertiesValid(
        workoutProperties: CalisthenicsModel
    ): CalisthenicsValidator {
        val workoutTime = workoutProperties.time
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(addWorkoutDetailUiState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(addWorkoutDetailUiState.sets)
        val isTimeValid =
            if (workoutTime != null) editWorkoutUseCase.isTimeValidUseCase(workoutTime) else true
        val isWeightValid =
            if (workoutProperties.weight != null) {
                editWorkoutUseCase.isDoubleValidUseCase(
                    addWorkoutDetailUiState.weight
                )
            } else {
                true
            }
        updateWorkoutState(
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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

    private fun areWeightLiftingPropertiesValid(): WeightLiftingValidator {
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(addWorkoutDetailUiState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(addWorkoutDetailUiState.sets)
        val isWeightValid =
            editWorkoutUseCase.isDoubleValidUseCase(addWorkoutDetailUiState.weight)
        updateWorkoutState(
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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

    private fun addOrSubtractReps(
        editWorkoutFunction: EditWorkoutFunction,
        repValue: String
    ) {
        val newRepValue = editWorkoutUseCase.addOrSubtractIntegersUseCase(
            editWorkoutFunction = editWorkoutFunction,
            value = repValue
        )
        updateWorkoutState(
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                distance = newDistanceValue,
                isDistanceErrorVisible = false
            )
        )
    }

    private fun clearWorkoutFields(workoutTypeEnum: WorkoutTypeEnum) {
        when (workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
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
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        hour = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.MINUTE -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        minute = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.SECOND -> {
                updateWorkoutState(
                    newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                        second = value
                    )
                )
            }
        }
    }

    fun addWorkoutNameAndType(workoutName: String, workoutType: String) {
        updateWorkoutState(
            newAddWorkoutDetailUiState = addWorkoutDetailUiState.copy(
                workoutName = workoutName,
                workoutType = workoutType,
                workoutTypeEnum = getWorkoutType(workoutType)
            )
        )
    }

    private fun updateWorkoutState(newAddWorkoutDetailUiState: AddWorkoutDetailUiState) {
        addWorkoutDetailUiState = newAddWorkoutDetailUiState
    }

    private suspend fun addWorkoutToRealm(
        callback: (Result) -> Unit
    ) {
        val newId = ObjectId().toHexString()
        val newDate = LocalDate.now().formatToCommonDate()
        when (addWorkoutDetailUiState.workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                val newWorkoutModel = WorkoutModel(
                    id = newId,
                    workoutDetailsModel = WorkoutDetailsModel(
                        name = addWorkoutDetailUiState.workoutName,
                        icon = R.drawable.icon_dumbell,
                        workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
                        workoutPropertiesModel = WorkoutPropertiesModel
                            .WeightLiftingProps(addWorkoutDetailUiState.weightLiftingPropertyList)
                    ),
                    date = newDate
                )
                saveToRealm(
                    workoutModel = newWorkoutModel,
                    callback = callback
                )
            }

            WorkoutTypeEnum.CALISTHENICS -> {
                val newWorkoutModel = WorkoutModel(
                    id = newId,
                    workoutDetailsModel = WorkoutDetailsModel(
                        name = addWorkoutDetailUiState.workoutName,
                        icon = R.drawable.icon_person,
                        workoutTypeEnum = WorkoutTypeEnum.CALISTHENICS,
                        workoutPropertiesModel = WorkoutPropertiesModel
                            .CalisthenicsProps(addWorkoutDetailUiState.calisthenicsPropertyList)
                    ),
                    date = newDate
                )
                saveToRealm(
                    workoutModel = newWorkoutModel,
                    callback = callback
                )
            }

            WorkoutTypeEnum.CARDIO -> {
                val newWorkoutModel = WorkoutModel(
                    id = newId,
                    workoutDetailsModel = WorkoutDetailsModel(
                        name = addWorkoutDetailUiState.workoutName,
                        icon = R.drawable.icon_sprinting_person,
                        workoutTypeEnum = WorkoutTypeEnum.CARDIO,
                        workoutPropertiesModel = WorkoutPropertiesModel
                            .CardioProps(addWorkoutDetailUiState.cardioPropertyList)
                    ),
                    date = newDate
                )
                saveToRealm(
                    workoutModel = newWorkoutModel,
                    callback = callback
                )
            }

            null -> callback(Result.FAILURE)
        }
    }

    private suspend fun saveToRealm(
        workoutModel: WorkoutModel,
        callback: (Result) -> Unit
    ) {
        try {
            val realmWorkout = workoutModel.toRealmWorkoutEntry(addWorkoutDetailUiState.workoutType)
            val isAddSuccess =
                realmWorkoutEntryUseCase.addSingleWorkoutEntryToRealmDbUseCase(realmWorkout)
            if (isAddSuccess) callback(Result.SUCCESS) else callback(Result.FAILURE)
        } catch (e: IllegalArgumentException) {
            callback(Result.FAILURE)
        } catch (e: Exception) {
            callback(Result.FAILURE)
        }
    }
}
