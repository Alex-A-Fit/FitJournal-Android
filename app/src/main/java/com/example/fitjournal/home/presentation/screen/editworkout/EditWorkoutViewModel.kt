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
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import com.example.fitjournal.home.presentation.model.enum.EditWorkoutListFunctions
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState
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
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        isRepsErrorVisible = false
                    )
                )
            }

            is EditWorkoutEvents.EditSets -> {
                addOrSubtractSets(
                    editWorkoutFunction = event.editWorkoutFunction,
                    setValue = event.setValue
                )
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        isSetsErrorVisible = false
                    )
                )
            }

            is EditWorkoutEvents.EditWeight -> {
                addOrSubtractWeight(
                    editWorkoutFunction = event.editWorkoutFunction,
                    weightValue = event.weightValue,
                    valueDifferential = event.valueDifferential
                )
                updateWorkoutState(
                    newEditWorkoutUiState = editWorkoutState.copy(
                        isWeightErrorVisible = false
                    )
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

            is EditWorkoutEvents.AddNewWeightTrainingItem -> {
                val workoutValidity = areWeightLiftingPropertiesValid()
                if (workoutValidity.isWorkoutValid()) {
                    val newPropsModel = getNewWorkoutPropertiesModel(
                        workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
                        editWorkoutFunction = EditWorkoutListFunctions.ADD_WORKOUT_ITEM,
                        newWeightLiftingItem = event.newWeightLiftingItem
                    )
                    // TODO: Handle error when weight training cant be added in if block
                    if (newPropsModel == null) return
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
                                        weightLiftingPropertyList = newPropsModel.getWeightLiftingProps()
                                            .toMutableStateList(),
                                        reps = "",
                                        sets = "",
                                        weight = ""
                                    )
                                )
                            } else {
                                // TODO: Handle error when weight training cant be added
                            }
                        }
                    } catch (e: Exception) {
                        Unit
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
                                    weightLiftingPropertyList = newPropertiesModel.getWeightLiftingProps()
                                        .toMutableStateList(),
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
                                    Triple(
                                        mutableStateListOf(),
                                        mutableStateListOf(),
                                        workoutList.props.toMutableStateList()
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
        updateWorkoutState(newEditWorkoutUiState = editWorkoutState.copy(reps = newRepValue))
    }

    private fun addOrSubtractSets(
        editWorkoutFunction: EditWorkoutFunction,
        setValue: String
    ) {
        val newSetValue = editWorkoutUseCase.addOrSubtractIntegersUseCase(
            editWorkoutFunction = editWorkoutFunction,
            value = setValue
        )
        updateWorkoutState(newEditWorkoutUiState = editWorkoutState.copy(sets = newSetValue))
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
        updateWorkoutState(newEditWorkoutUiState = editWorkoutState.copy(weight = newWeightValue))
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
                        time = "",
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
                        time = "",
                        distance = "",
                        distanceType = CardioDistanceType.MILES,
                        isLapsErrorVisible = false,
                        isTimeErrorVisible = false,
                        isDistanceErrorVisible = false
                    )
                )
            }
        }
    }
}
