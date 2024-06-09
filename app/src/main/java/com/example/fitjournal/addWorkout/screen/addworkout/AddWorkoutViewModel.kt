package com.example.fitjournal.addWorkout.screen.addworkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.R
import com.example.fitjournal.addWorkout.model.AddWorkoutUiModel
import com.example.fitjournal.addWorkout.model.events.AddWorkoutEvents
import com.example.fitjournal.core.domain.model.WorkoutLibraryModel
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.filter.searchForText
import com.example.fitjournal.library.presentation.screen.library.utils.mapToLibraryUiList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(
    private val realmWorkoutLibraryUseCase: RealmWorkoutLibraryUseCase
) : ViewModel() {
    var addWorkoutUiState by mutableStateOf(
        AddWorkoutUiModel(
            handleAddWorkoutClickEvents = ::journalClickEvents
        )
    )
        private set

    private fun journalClickEvents(event: AddWorkoutEvents) {
        when (event) {
            is AddWorkoutEvents.FilterSearchByWorkout -> {
                val filteredList = searchForText(
                    event.workout,
                    addWorkoutUiState.masterWorkoutList
                )
                updateJournalEntryState(
                    newJournalEntryState = addWorkoutUiState.copy(
                        listOfSearchedWorkouts = filteredList.toMutableStateList(),
                        searchedTerm = event.workout
                    )
                )
            }

            AddWorkoutEvents.ClearSearchBarFilter -> {
                updateJournalEntryState(
                    newJournalEntryState = addWorkoutUiState.copy(
                        listOfSearchedWorkouts = addWorkoutUiState.masterWorkoutList.toMutableStateList(),
                        searchedTerm = ""
                    )
                )
            }

            is AddWorkoutEvents.AddWorkoutToLibrary -> {
                addWorkoutToLibraryDatabase(
                    workoutName = event.workout.workoutName,
                    workoutType = event.context.getString(event.workout.workoutType.stringId),
                    workoutTypeEnum = event.workout.workoutType,
                    successCallback = {
                        getDataFromRealmDb()
                        viewModelScope.launch {
                            event.showSnackBar(
                                event.context.getString(
                                    event.workout.snackBarMessageId,
                                    event.workout.workoutName
                                )
                            )
                        }
                    },
                    errorCallback = {
                        viewModelScope.launch {
                            event.showSnackBar(
                                event.context.getString(
                                    R.string.error_with_workout_being_added_to_library,
                                    event.workout.workoutName
                                )
                            )
                        }
                    }
                )
            }

            AddWorkoutEvents.SyncRealmWorkoutEntryFromDb -> {
                val shouldSyncOccur = realmWorkoutLibraryUseCase.syncRealmWorkoutLibraryUseCase()
                if (shouldSyncOccur) {
                    getDataFromRealmDb()
                }
            }
        }
    }

    fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutLibraryUseCase.getRealmWorkoutLibraryList()
            if (workoutList.isNotEmpty()) {
                val libraryList = workoutList.groupBy { it.workoutType }.toSortedMap()
                val masterWorkoutList = mapToLibraryUiList(libraryList)
                updateJournalEntryState(
                    newJournalEntryState = addWorkoutUiState.copy(
                        masterWorkoutList = masterWorkoutList,
                        listOfSearchedWorkouts = if (addWorkoutUiState.searchedTerm.isEmpty()) {
                            masterWorkoutList.toMutableStateList()
                        } else {
                            searchForText(
                                addWorkoutUiState.searchedTerm,
                                addWorkoutUiState.masterWorkoutList
                            ).toMutableStateList()
                        }
                    )
                )
            }
        }
    }

    private fun addWorkoutToLibraryDatabase(
        workoutName: String,
        workoutType: String,
        workoutTypeEnum: WorkoutTypeEnum,
        successCallback: suspend () -> Unit,
        errorCallback: suspend () -> Unit
    ) {
        viewModelScope.launch {
            val wasLibraryItemAdded =
                realmWorkoutLibraryUseCase.addSingleLibraryItemToRealmDbUseCase(
                    workoutLibraryModel = WorkoutLibraryModel(
                        name = workoutName,
                        workoutType = workoutType,
                        workoutTypeEnum = workoutTypeEnum
                    )
                )
            if (wasLibraryItemAdded) {
                successCallback()
            } else {
                errorCallback()
            }
        }
    }

    private fun updateJournalEntryState(newJournalEntryState: AddWorkoutUiModel) {
        addWorkoutUiState = newJournalEntryState
    }
}
