package com.example.fitjournal.journalEntry.screen.journalEntry.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.data.util.getWorkoutType
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.domain.usecase.workout.EditWorkoutUseCase
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.EditWorkoutListFunctions
import com.example.fitjournal.home.presentation.model.ui.CalisthenicsValidator
import com.example.fitjournal.home.presentation.model.ui.CardioValidator
import com.example.fitjournal.home.presentation.model.ui.WeightLiftingValidator
import com.example.fitjournal.journalEntry.model.JournalEntryDetailsUiState
import com.example.fitjournal.journalEntry.model.events.JournalEntryDetailsEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWorkoutDetailViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase,
    private val editWorkoutUseCase: EditWorkoutUseCase
) : ViewModel() {
    var journalEntryDetailsUiState: JournalEntryDetailsUiState by mutableStateOf(
        JournalEntryDetailsUiState(
            journalEntryDetailsEvents = ::journalEntryDetailsEvents
        )
    )
        private set

    fun journalEntryDetailsEvents(event: JournalEntryDetailsEvents) {
        when (event) {
            is JournalEntryDetailsEvents.AddNewCalisthenicSetToWorkout -> {
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
                        newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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

            is JournalEntryDetailsEvents.AddNewCardioSetToWorkout -> {
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
                        newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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

            is JournalEntryDetailsEvents.AddNewWeightTrainingSetToWorkout -> {
                val workoutValidity = areWeightLiftingPropertiesValid()
                if (workoutValidity.isWorkoutValid()) {
                    val newPropsModel = getNewWorkoutPropertiesModel(
                        workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
                        editWorkoutFunction = EditWorkoutListFunctions.ADD_WORKOUT_ITEM,
                        newWeightLiftingItem = event.newWeightLiftingItem
                    )
                    updateWorkoutState(
                        newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                            weightLiftingPropertyList = newPropsModel?.getWeightLiftingProps()
                                ?: mutableStateListOf(),
                            reps = "",
                            sets = "",
                            weight = ""
                        )
                    )
                }
            }

            is JournalEntryDetailsEvents.ClearWorkoutTextFields -> {
                clearWorkoutFields(event.workoutTypeEnum)
            }

            is JournalEntryDetailsEvents.DeleteWorkoutSetItemInWorkoutModelList -> {
                val workoutType = journalEntryDetailsUiState.workoutTypeEnum
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
                        newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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

            is JournalEntryDetailsEvents.EditDistance -> {
                addOrSubtractDistance(
                    editWorkoutFunction = event.editWorkoutFunction,
                    distanceValue = event.value
                )
            }

            JournalEntryDetailsEvents.EditDistanceType -> {
                val newDistanceType = journalEntryDetailsUiState.distanceType.getOtherDistanceType()
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        distanceType = newDistanceType
                    )
                )
            }

            is JournalEntryDetailsEvents.EditLaps -> {
                addOrSubtractLaps(
                    editWorkoutFunction = event.editWorkoutFunction,
                    lapValue = event.value
                )
            }

            is JournalEntryDetailsEvents.EditReps -> {
                addOrSubtractReps(
                    editWorkoutFunction = event.editWorkoutFunction,
                    repValue = event.repValue
                )
            }

            is JournalEntryDetailsEvents.EditSets -> {
                addOrSubtractSets(
                    editWorkoutFunction = event.editWorkoutFunction,
                    setValue = event.setValue
                )
            }

            is JournalEntryDetailsEvents.EditTime -> {
                updateTimeValue(
                    value = event.value,
                    timeDeterminate = event.timeDeterminate
                )
            }

            is JournalEntryDetailsEvents.EditWeight -> {
                addOrSubtractWeight(
                    editWorkoutFunction = event.editWorkoutFunction,
                    weightValue = event.weightValue,
                    valueDifferential = event.valueDifferential
                )
            }

            JournalEntryDetailsEvents.EditWeightType -> {
                val getOtherWeightType = journalEntryDetailsUiState.weightType.getOtherWeightType()
                updateWorkoutState(
                    journalEntryDetailsUiState.copy(
                        weightType = getOtherWeightType
                    )
                )
            }

            is JournalEntryDetailsEvents.OnDistanceValueChange -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        distance = event.distanceValue,
                        isDistanceErrorVisible = false
                    )
                )
            }

            is JournalEntryDetailsEvents.OnLapsValueChange -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        laps = event.lapValue,
                        isLapsErrorVisible = false
                    )
                )
            }

            is JournalEntryDetailsEvents.OnRepValueChange -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        reps = event.repValue,
                        isRepsErrorVisible = false
                    )
                )
            }

            is JournalEntryDetailsEvents.OnSetValueChange -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        sets = event.setValue,
                        isSetsErrorVisible = false
                    )
                )
            }

            is JournalEntryDetailsEvents.OnWeightValueChange -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        weight = event.weightValue,
                        isWeightErrorVisible = false
                    )
                )
            }

            is JournalEntryDetailsEvents.UpdateWorkoutListItem -> TODO()
            JournalEntryDetailsEvents.ClearViewModelState -> {
                journalEntryDetailsUiState = JournalEntryDetailsUiState(
                    workoutName = journalEntryDetailsUiState.workoutName,
                    workoutType = journalEntryDetailsUiState.workoutType,
                    workoutTypeEnum = journalEntryDetailsUiState.workoutTypeEnum,
                    journalEntryDetailsEvents = ::journalEntryDetailsEvents
                )
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
                                journalEntryDetailsUiState.weightLiftingPropertyList
                            weightTrainingWorkoutList.add(newWeightLiftingItem)
                            WorkoutPropertiesModel
                                .WeightLiftingProps(weightTrainingWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val weightTrainingWorkoutList =
                            journalEntryDetailsUiState.weightLiftingPropertyList
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
                                journalEntryDetailsUiState.calisthenicsPropertyList
                            calisthenicsWorkoutList.add(newCalisthenicsItem)
                            WorkoutPropertiesModel
                                .CalisthenicsProps(calisthenicsWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val calisthenicsWorkoutList =
                            journalEntryDetailsUiState.calisthenicsPropertyList
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
                                journalEntryDetailsUiState.cardioPropertyList
                            cardioWorkoutList.add(newCardioItem)
                            WorkoutPropertiesModel
                                .CardioProps(cardioWorkoutList)
                        } else {
                            null
                        }
                    }

                    EditWorkoutListFunctions.DELETE_WORKOUT_ITEM -> {
                        val cardioWorkoutList = journalEntryDetailsUiState.cardioPropertyList
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
        val isLapsValid = if (journalEntryDetailsUiState.laps != "") {
            editWorkoutUseCase.isDoubleValidUseCase(journalEntryDetailsUiState.laps)
        } else {
            true
        }
        val isDistanceValid =
            editWorkoutUseCase.isDoubleValidUseCase(journalEntryDetailsUiState.distance)
        val isTimeValid = editWorkoutUseCase.isTimeValidUseCase(workoutTime)
        updateWorkoutState(
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(journalEntryDetailsUiState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(journalEntryDetailsUiState.sets)
        val isTimeValid =
            if (workoutTime != null) editWorkoutUseCase.isTimeValidUseCase(workoutTime) else true
        val isWeightValid =
            if (workoutProperties.weight != null) {
                editWorkoutUseCase.isDoubleValidUseCase(
                    journalEntryDetailsUiState.weight
                )
            } else {
                true
            }
        updateWorkoutState(
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
        val isRepsValid = editWorkoutUseCase.isIntegerValidUseCase(journalEntryDetailsUiState.reps)
        val isSetsValid = editWorkoutUseCase.isIntegerValidUseCase(journalEntryDetailsUiState.sets)
        val isWeightValid =
            editWorkoutUseCase.isDoubleValidUseCase(journalEntryDetailsUiState.weight)
        updateWorkoutState(
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                distance = newDistanceValue,
                isDistanceErrorVisible = false
            )
        )
    }

    private fun clearWorkoutFields(workoutTypeEnum: WorkoutTypeEnum) {
        when (workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
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
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        hour = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.MINUTE -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        minute = value
                    )
                )
            }

            EditWorkoutTimeDeterminate.SECOND -> {
                updateWorkoutState(
                    newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                        second = value
                    )
                )
            }
        }
    }

    fun addWorkoutNameAndType(workoutName: String, workoutType: String) {
        updateWorkoutState(
            newJournalEntryDetailsUiState = journalEntryDetailsUiState.copy(
                workoutName = workoutName,
                workoutType = workoutType,
                workoutTypeEnum = getWorkoutType(workoutType)
            )
        )
    }

    private fun updateWorkoutState(newJournalEntryDetailsUiState: JournalEntryDetailsUiState) {
        journalEntryDetailsUiState = newJournalEntryDetailsUiState
    }
}
