package com.example.fitjournal.addWorkout.screen.addworkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.addWorkout.model.AddWorkoutUiModel
import com.example.fitjournal.addWorkout.model.events.AddWorkoutEvents
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
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
            handleJournalEntryClickEvents = ::journalClickEvents
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

    private fun updateJournalEntryState(newJournalEntryState: AddWorkoutUiModel) {
        addWorkoutUiState = newJournalEntryState
    }
}
